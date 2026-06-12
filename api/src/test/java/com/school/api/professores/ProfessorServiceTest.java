package com.school.api.professores;

import com.school.api.endereco.EnderecoService;
import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.infra.erros.RegraNegocioException;
import com.school.api.infra.erros.professores.ProfessorNaoEncontradoException;
import com.school.api.professores.dto.DadosAtualizacaoProfessores;
import com.school.api.professores.dto.DadosCadastroProfessores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.school.api.professores.Especialidade.MATEMATICA;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessorServiceTest {

    @InjectMocks
    private ProfessorService professorService;

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private EnderecoService enderecoService;

    private DadosCadastroProfessores dadosCadastroProfessores;
    private DadosEndereco endereco;
    private Professor professor;

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

        dadosCadastroProfessores = new DadosCadastroProfessores(
                "João Silva",
                "joaoslv@gmail.com",
                "11234567890",
                "12345678909",
                MATEMATICA,
                endereco
        );

        professor = mock(Professor.class);
    }

    @Test
    void deveCadastrarProfessorComSucesso(){
        when(enderecoService.preencherEndereco(any())).thenReturn(endereco);
        when(professorRepository.existsByCpf(anyString())).thenReturn(false);

        professorService.cadastrar(dadosCadastroProfessores);

        verify(professorRepository).save(any(Professor.class));
    }

    @Test
    void naoDeveCadastrarProfessorComCpfDuplicado(){
        when(enderecoService.preencherEndereco(any())).thenReturn(endereco);
        when(professorRepository.existsByCpf((anyString()))).thenReturn(true);

        assertThrows(RegraNegocioException.class, () -> professorService.cadastrar(dadosCadastroProfessores));

        verify(professorRepository, never()).save(any());
    }

    @Test
    void deveAtualizarProfessorComSucesso(){
        Long id = 1L;

        when(professorRepository.findById(id)).thenReturn(Optional.of(professor));

        DadosAtualizacaoProfessores dados = new DadosAtualizacaoProfessores(
                "Novo Nome",
                "11888888888",
                null
        );

        professorService.atualizar(id, dados);

        verify(professor).atualizarNome("Novo Nome");
        verify(professor).atualizarTelefone(anyString());
    }

    @Test
    void deveLancarExcecaoAoAtualizarProfessorInexistente(){

        Long id = 1L;

        when(professorRepository.findById(id))
                .thenReturn(Optional.empty());

        DadosAtualizacaoProfessores dados = new DadosAtualizacaoProfessores(
                "Novo Nome",
                null,
                null
        );
        assertThrows(ProfessorNaoEncontradoException.class, () -> professorService.atualizar(id, dados));
    }

    @Test
    void deveExcluirProfessorComSucesso(){

        Long id = 1L;

        when(professorRepository.findById(id)).thenReturn(Optional.of(professor));
        professorService.excluir(id);

        verify(professor).excluir();
    }

    @Test
    void deveLancarExcecaoAoExcluirProfessorInexistente(){
        Long id = 1L;

        when(professorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProfessorNaoEncontradoException.class, () -> professorService.excluir(id));
    }
}
