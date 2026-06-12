package com.school.api.aluno;

import com.school.api.aluno.dto.DadosAtualizacaoAlunos;
import com.school.api.aluno.dto.DadosCadastroAlunos;
import com.school.api.aluno.dto.DadosDetalhamentoAluno;
import com.school.api.endereco.Endereco;
import com.school.api.endereco.EnderecoService;
import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.infra.erros.RegraNegocioException;
import com.school.api.infra.erros.alunos.AlunoNaoEncontradoException;
import com.school.api.professores.dto.DadosAtualizacaoProfessores;
import com.school.api.turma.Serie;
import com.school.api.turma.Turma;
import com.school.api.turma.TurmaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlunoServiceTest {

    @InjectMocks
    private AlunoService service;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private TurmaRepository turmaRepository;

    private DadosCadastroAlunos dadosCadastroAlunos;
    private DadosEndereco endereco;
    private Aluno aluno;

    @BeforeEach
    void setUp(){

        endereco = new DadosEndereco(
                "85870-000",
                "Rua Goiânia",
                "123",
                "Itaipu C",
                "Foz do Iguaçu",
                "PR",
                "Casa 2"
        );

        dadosCadastroAlunos = new DadosCadastroAlunos(
                "Maria Silva",
                "marva@gmail.com",
                "12345678900",
                LocalDate.parse("2010-01-01"),
                "João Silva",
                "12123456789",
                Serie.SEGUNDO_EM,
                endereco
        );

        aluno = mock(Aluno.class);
    }

    @Test
    void deveCadastrarAlunoComSucesso(){
        Turma turma = mock(Turma.class);

        when(alunoRepository.existsByCpf(anyString()))
                .thenReturn(false);
        when(enderecoService.preencherEndereco(any())).thenReturn(endereco);
        when(turmaRepository.buscarTurmasOrdenadasPorSerieOrdenadas(any())).thenReturn(List.of(turma));
        when(turma.getAlunos()).thenReturn(new ArrayList<>());
        when(turma.getCapacidade()).thenReturn(40);

        service.cadastrar(dadosCadastroAlunos);

        verify(alunoRepository).save(any(Aluno.class));
    }

    @Test
    void naoDeveCadastrarAlunoComCpfDuplicado(){
        when(alunoRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(RegraNegocioException.class, () -> service.cadastrar(dadosCadastroAlunos));

        verify(alunoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoNaoExistirTurmaDisponivel(){
        when(alunoRepository.existsByCpf(anyString())).thenReturn(false);
        when(enderecoService.preencherEndereco(any())).thenReturn(endereco);
        when(turmaRepository.buscarTurmasOrdenadasPorSerieOrdenadas(any())).thenReturn(List.of());

        assertThrows(RuntimeException.class, () -> service.cadastrar(dadosCadastroAlunos));

        verify(alunoRepository, never()).save(any());
    }

    @Test
    void deveAtualizarComSucesso(){
        Long id = 1L;

        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        DadosAtualizacaoAlunos dadosAtualizacaoAlunos = new DadosAtualizacaoAlunos(
                "Novo Nome",
                "novo@email.com",
                "11999999999",
                null
        );

        service.atualizar(id, dadosAtualizacaoAlunos);

        verify(aluno).atualizarNome("Novo Nome");
        verify(aluno).atualizarEmail("novo@email.com");
        verify(aluno).atualizarTelefoneResponsavel("11999999999");

    }

    @Test
    void deveAtualizarEnderecoDoAluno(){
        Long id = 1L;

        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));
        when(enderecoService.preencherEndereco(any())).thenReturn(endereco);

        DadosAtualizacaoAlunos dados = new DadosAtualizacaoAlunos(
                null,
                null,
                null,
                endereco
        );
        service.atualizar(id, dados);

        verify(aluno).atualizarEndereco(any(Endereco.class));
    }

    @Test
    void deveLancarExcecaoAoAtualizarAlunoInexistente(){
        Long id = 1L;

        when(alunoRepository.findById(id)).thenReturn(Optional.empty());

        DadosAtualizacaoAlunos dados = new DadosAtualizacaoAlunos(
                "Novo nome",
                null,
                null,
                null
        );

        assertThrows(AlunoNaoEncontradoException.class, () -> service.atualizar(id, dados));
    }

    @Test
    void deveExcluirAlunosComSucesso(){
        Long id = 1L;

        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        service.excluir(id);

        verify(aluno).excluir();
    }

    @Test
    void deveLancarExcecaoAoExcluirALunoInexistente(){
        Long id = 1L;

        when(alunoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AlunoNaoEncontradoException.class, () -> service.excluir(id));
    }

    @Test
    void deveDetalharAlunoComSucesso(){
        Long id = 1L;

        when(alunoRepository.findById(id)).thenReturn(Optional.of(aluno));

        DadosDetalhamentoAluno resultado = service.detalhar(id);

        assertNotNull(resultado);
    }
}
