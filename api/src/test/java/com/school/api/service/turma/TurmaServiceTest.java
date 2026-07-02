package com.school.api.service.turma;

import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.aluno.disciplina.DisciplinaRepository;
import com.school.api.infra.erros.RegraNegocioException;
import com.school.api.infra.erros.disciplina.DisciplinaNaoEncontradaException;
import com.school.api.infra.erros.turmas.TurmaNaoEncontradaException;
import com.school.api.turma.Serie;
import com.school.api.turma.Turma;
import com.school.api.turma.TurmaRepository;
import com.school.api.turma.TurmaService;
import com.school.api.turma.dto.DadosTurmaDetalhada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TurmaServiceTest {

    @InjectMocks
    private TurmaService turmaService;

    @Mock
    private TurmaRepository turmaRepository;

    @Mock
    private DisciplinaRepository disciplinaRepository;

    private Turma turma;
    private Disciplina disciplina;

    @BeforeEach
    void setUp(){
        turma = mock(Turma.class);
        disciplina = mock(Disciplina.class);
    }

    @Test
    @DisplayName("Deve adicionar disciplina na turma")
    void deveAdicionarDisciplinaNaTurma(){
        Long turmaId = 1L;
        Long disciplinaId = 1L;

        List<Disciplina> disciplinas = new ArrayList<>();

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turma));
        when(disciplinaRepository.findById(disciplinaId)).thenReturn(Optional.of(disciplina));
        when(turma.getDisciplinas()).thenReturn(disciplinas);

        turmaService.adicionarDisciplina(turmaId, disciplinaId);

        verify(turmaRepository).save(turma);

        assertTrue(disciplinas.contains(disciplina));
    }

    @Test
    @DisplayName("Deve lançar exceção quando turma não existir")
    void deveLancarExcecaoQuandoTurmaNaoExistir(){
        Long turmaId = 1L;
        Long disciplinaId = 1L;

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.empty());

        assertThrows(TurmaNaoEncontradaException.class, () -> turmaService.adicionarDisciplina(turmaId, disciplinaId));

        verify(disciplinaRepository, never()).findById(anyLong());
        verify(turmaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando disciplina não existir")
    void deveLancarExcecaoQuandoDisciplinaNaoExistir(){
        Long turmaId = 1L;
        Long disciplinaId = 1L;

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turma));
        when(disciplinaRepository.findById(disciplinaId)).thenReturn(Optional.empty());

        assertThrows(DisciplinaNaoEncontradaException.class, () -> turmaService.adicionarDisciplina(turmaId, disciplinaId));

        verify(turmaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Não deve adicionar disciplina duplicada")
    void naoDeveAdicionarDisciplinaDuplicada(){
        Long turmaId = 1L;
        Long disciplinaId = 1L;

        List<Disciplina> disciplinas = new ArrayList<>();
        disciplinas.add(disciplina);

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turma));
        when(disciplinaRepository.findById(disciplinaId)).thenReturn(Optional.of(disciplina));
        when(turma.getDisciplinas()).thenReturn(disciplinas);

        assertThrows(RegraNegocioException.class, () -> turmaService.adicionarDisciplina(turmaId, disciplinaId));

        verify(turmaRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar disciplinas da turma")
    void deveListarDisciplinasDaTurma(){
        Long turmaId = 1L;

        Disciplina disciplina1 = mock(Disciplina.class);
        Disciplina disciplina2 = mock(Disciplina.class);

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turma));
        when(turma.getDisciplinas()).thenReturn(List.of(disciplina1, disciplina2));
        when(disciplina1.getNome()).thenReturn("Matemática");
        when(disciplina2.getNome()).thenReturn("Física");

        List<String> resultado = turmaService.listarDisciplinas(turmaId);

        assertAll(
                () -> assertEquals(2, resultado.size()),
                () -> assertTrue(resultado.contains("Matemática")),
                () -> assertTrue(resultado.contains("Física"))
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao listar turma inexistente")
    void deveLancarExcecaoAoListarTurmaInexistente(){
        Long turmaId = 1L;

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.empty());

        assertThrows(TurmaNaoEncontradaException.class, () -> turmaService.listarDisciplinas(turmaId));
    }

    @Test
    @DisplayName("Deve detalhar turma")
    void deveDetalharTurma(){
        Long turmaId = 1L;

        Disciplina disciplina = mock(Disciplina.class);

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.of(turma));
        when(turma.getId()).thenReturn(turmaId);
        when(turma.getNome()).thenReturn("Turma A");
        when(turma.getSerie()).thenReturn(Serie.PRIMEIRO_EM);
        when(turma.getDisciplinas()).thenReturn(List.of(disciplina));
        when(disciplina.getNome()).thenReturn("Matemática");

        DadosTurmaDetalhada resultado = turmaService.detalhar(turmaId);

        assertAll(
                () -> assertNotNull(resultado),
                () -> assertEquals(turmaId, resultado.id()),
                () -> assertEquals("Turma A", resultado.nome()),
                () -> assertEquals(Serie.PRIMEIRO_EM, resultado.serie()),
                () ->assertEquals(List.of("Matemática"), resultado.disciplinas())
        );
    }

    @Test
    @DisplayName("Deve lançar exceção ao detalhar turma inexistente")
    void deveLancarExcecaoAoDetalharTurmaInexistente(){
        Long turmaId = 1L;

        when(turmaRepository.findById(turmaId)).thenReturn(Optional.empty());

        assertThrows(TurmaNaoEncontradaException.class, () -> turmaService.detalhar(turmaId));
    }
}
