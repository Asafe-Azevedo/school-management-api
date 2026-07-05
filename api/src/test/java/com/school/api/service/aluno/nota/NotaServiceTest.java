package com.school.api.service.aluno.nota;

import com.school.api.aluno.Aluno;
import com.school.api.aluno.AlunoRepository;
import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.aluno.disciplina.DisciplinaRepository;

import com.school.api.aluno.nota.Nota;
import com.school.api.aluno.nota.NotaRepository;
import com.school.api.aluno.nota.NotaService;
import com.school.api.aluno.nota.TipoNota;
import com.school.api.infra.erros.RegraNegocioException;
import com.school.api.turma.Turma;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotaServiceTest {

    @Mock
    private NotaRepository notaRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private NotaService notaService;

    private Aluno aluno;
    private Turma turma;
    private Disciplina disciplina;

    @BeforeEach
    void setUp(){
        aluno = mock(Aluno.class);
        turma = mock(Turma.class);
        disciplina = mock(Disciplina.class);
    }

    @Test
    @DisplayName("Deve lançar nota com sucesso")
    void deveLancarNotaComSucesso(){

        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        disciplinas.add(disciplina);

        when(aluno.getTurma()).thenReturn(turma);
        when(turma.getDisciplinas()).thenReturn(disciplinas);
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));
        when(notaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Nota nota = notaService.lancarNota(
                1L,
                1L,
                8.0,
                TipoNota.PROVA,
                1
        );

        assertAll(
                () -> assertNotNull(nota),
                () -> assertEquals(8.0, nota.getValor()),
                () -> assertEquals(TipoNota.PROVA, nota.getTipo()),
                () -> assertEquals(1, nota.getBimestre())
        );

        verify(notaRepository).save(any(Nota.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nota maior que dez")
    void deveLancarExcecaoQuandoNotaMaiorQueDez(){

        when(aluno.getTurma()).thenReturn(turma);
        when(turma.getDisciplinas()).thenReturn(List.of(disciplina));
        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
        when(disciplinaRepository.findById(1L)).thenReturn(Optional.of(disciplina));

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> notaService.lancarNota(
                        1L,
                        1L,
                        11.0,
                        TipoNota.PROVA,
                        1
                )
        );

        assertEquals("Nota deve ser entre 0 e 10", exception.getMessage());

        verify(notaRepository, never()).save(any(Nota.class));
    }












}
