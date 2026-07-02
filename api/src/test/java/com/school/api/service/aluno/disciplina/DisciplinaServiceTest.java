package com.school.api.service.aluno.disciplina;

import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.aluno.disciplina.DisciplinaRepository;
import com.school.api.aluno.disciplina.DisciplinaService;
import com.school.api.aluno.disciplina.dto.DadosAtualizacaoDisciplina;
import com.school.api.aluno.disciplina.dto.DadosCadastroDisciplina;
import com.school.api.aluno.disciplina.dto.DadosDetalhamentoDisciplina;
import com.school.api.infra.erros.disciplina.DisciplinaNaoEncontradaException;
import com.school.api.infra.erros.professores.ProfessorNaoEncontradoException;
import com.school.api.professores.Professor;
import com.school.api.professores.ProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisciplinaServiceTest {

    @InjectMocks
    private DisciplinaService disciplinaService;

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @Mock
    private ProfessorRepository professorRepository;

    private DadosCadastroDisciplina dadosCadastroDisciplina;
    private Professor professor;
    private Disciplina disciplina;
    private DadosAtualizacaoDisciplina dadosAtualizacaoDisciplina;

    @BeforeEach
    void setUp(){
        dadosCadastroDisciplina = new DadosCadastroDisciplina(
                "Matemática",
                1L
        );
        dadosAtualizacaoDisciplina = new DadosAtualizacaoDisciplina(2L);
        professor = mock(Professor.class);
        disciplina = mock(Disciplina.class);

    }

    @Test
    @DisplayName("Deve cadastar disciplina com sucesso")
    void deveCadastrarDisciplinaComSucesso(){
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));
        when(disciplinaRepository.save(any(Disciplina.class))).thenReturn(disciplina);

        Disciplina resultado = disciplinaService.cadastrar(dadosCadastroDisciplina);

        assertEquals(disciplina, resultado);

        verify(professorRepository).findById(1L);
        verify(disciplinaRepository).save(any(Disciplina.class));
    }

    @Test
    @DisplayName("Não deve cadastar disciplina quando professor não existir")
    void naoDeveCadastrarDisciplinaQuandoProfessorNaoExistir(){
        when(professorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProfessorNaoEncontradoException.class, () -> disciplinaService.cadastrar(dadosCadastroDisciplina));

        verify(disciplinaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve associar professor corretamente na disciplina")
    void deveAssociarProfessorCorretamenteNaDisciplina(){
        when(professorRepository.findById(1L)).thenReturn(Optional.of(professor));

        disciplinaService.cadastrar(dadosCadastroDisciplina);

        verify(disciplinaRepository).save(argThat(disciplina -> disciplina.getNome().equals("Matemática") && disciplina.getProfessor().equals(professor)));
    }

    @Test
    @DisplayName("Deve detalhar discplina")
    void deveDetalharDisciplina(){
        when(professor.getNome()).thenReturn("João Silva");
        when(disciplina.getId()).thenReturn(1L);
        when(disciplina.getNome()).thenReturn("Matemática");
        when(disciplina.getProfessor()).thenReturn(professor);
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));

        DadosDetalhamentoDisciplina resultado = disciplinaService.detalhar(1L);

        assertAll(
                () -> assertNotNull(resultado),
                () -> assertEquals(1L, resultado.id()),
                () -> assertEquals("Matemática", resultado.nome()),
                () -> assertEquals("João Silva", resultado.professor())
        );

        verify(disciplinaRepository).findById(1L);

    }

    @Test
    @DisplayName("Deve lançar exeção ao detalhar disciplina inexistente")
    void deveLancarExcecaoAoDetalharDisciplinaInexistente(){
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisciplinaNaoEncontradaException.class, () -> disciplinaService.detalhar(1L));
    }

    @Test
    @DisplayName("Deve atualizar professor responsável")
    void deveAtualizarProfessorResponsavel(){
        Professor novoProfessor = mock(Professor.class);

        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        when(professorRepository.findById(2L)).thenReturn(Optional.of(novoProfessor));

        disciplinaService.atualizar(1L, dadosAtualizacaoDisciplina);

        verify(disciplina).atualizarProfessor(novoProfessor);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar disciplina inexistente")
    void deveLancarExcecaoAoAtualizarDisciplinaInexistente(){
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisciplinaNaoEncontradaException.class, () -> disciplinaService.atualizar(1L, dadosAtualizacaoDisciplina));

        verify(professorRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar com professor inexistente")
    void deveLancarExcecaoAoAtualizarComProfessorInexistente(){
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        when(professorRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ProfessorNaoEncontradoException.class, () -> disciplinaService.atualizar(1L, dadosAtualizacaoDisciplina));

        verify(disciplina, never()).atualizarProfessor(any());
    }

    @Test
    @DisplayName("Deve excluoir disciplina")
    void deveExcluirDisciplina(){
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));

        disciplinaService.excluir(1L);

        verify(disciplina).excluir();
    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir disciplina inexistente")
    void deveLancarExcecaoAoExcluirDisciplinaInexistente(){
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DisciplinaNaoEncontradaException.class, () -> disciplinaService.excluir(1L));
    }
}
