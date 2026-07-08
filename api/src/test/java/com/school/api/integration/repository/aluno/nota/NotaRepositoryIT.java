package com.school.api.integration.repository.aluno.nota;

import com.school.api.aluno.AlunoRepository;
import com.school.api.aluno.disciplina.DisciplinaRepository;
import com.school.api.aluno.nota.NotaRepository;
import com.school.api.professores.ProfessorRepository;
import com.school.api.integration.support.factory.aluno.AlunoFactory;
import com.school.api.integration.support.factory.aluno.disciplina.DisciplinaFactory;
import com.school.api.integration.support.factory.aluno.nota.NotaFactory;
import com.school.api.integration.support.factory.professor.ProfessorFactory;
import com.school.api.integration.support.integration.BaseRepositoryIT;
import com.school.api.integration.support.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NotaRepository")
class NotaRepositoryIT extends BaseRepositoryIT {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Nested
    @DisplayName("findByAlunoId")
    class FindByAlunoId{

        @Test
        @DisplayName("Deve buscar notas por aluno")
        void deveBuscarNotasPorAluno(){
            var professor = professorRepository.save(ProfessorFactory.criarProfessor());
            var disciplina = disciplinaRepository.save(DisciplinaFactory.criarDisciplina(professor));
            var aluno = alunoRepository.save(AlunoFactory.criarAluno());
            notaRepository.save(NotaFactory.criarNota(aluno, disciplina));
            var notas = notaRepository.findByAlunoId(aluno.getId());

            assertAll(
                    () -> assertEquals(1, notas.size()),
                    () -> assertEquals(aluno.getId(), notas.get(0).getAluno().getId())
            );
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando aluno não possuir notas")
        void deveRetornarListaVaziaQuandoAlunoNaoPossuirNotas(){
            var notas = notaRepository.findByAlunoId(TestConstants.ID_INEXISTENTE);

            assertTrue(notas.isEmpty());
        }
    }

    @Nested
    @DisplayName("findByAlunoIdAndDisciplinaId")
    class FindByAlunoIdAndDisciplinaId{

        @Test
        @DisplayName("Deve buscar notas por aluno e disciplina")
        void deveBuscarNotasPorAlunoEDisciplina() {
            var professor = professorRepository.save(ProfessorFactory.criarProfessor());
            var disciplina = disciplinaRepository.save(DisciplinaFactory.criarDisciplina(professor));
            var aluno = alunoRepository.save(AlunoFactory.criarAluno());
            notaRepository.save(NotaFactory.criarNota(aluno, disciplina));
            var notas = notaRepository.findByAlunoIdAndDisciplinaId(aluno.getId(), disciplina.getId());

            assertAll(
                    () -> assertEquals(1, notas.size()),
                    () -> assertEquals(disciplina.getId(), notas.get(0).getDisciplina().getId()),
                    () -> assertEquals(aluno.getId(), notas.get(0).getAluno().getId())
            );
        }

        @Test
        @DisplayName("Não deve retornar notas de outra disciplina")
        void naoDeveRetornarNotasDeOutraDisciplina() {
            var professor = professorRepository.save(ProfessorFactory.criarProfessor());
            var disciplina1 = disciplinaRepository.save(DisciplinaFactory.criarDisciplina(professor));
            var disciplina2 = disciplinaRepository.save(DisciplinaFactory.criarDisciplina(professor));
            var aluno = alunoRepository.save(AlunoFactory.criarAluno());
            notaRepository.save(NotaFactory.criarNota(aluno, disciplina1));
            var notas = notaRepository.findByAlunoIdAndDisciplinaId(aluno.getId(), disciplina2.getId());

            assertTrue(notas.isEmpty());
        }
    }

    @Nested
    @DisplayName("Persistência")
    class PersistenciaIT{

        @Test
        @DisplayName("Deve salvar nota")
        void deveSalvarNota(){
            var professor = professorRepository.save(ProfessorFactory.criarProfessor());

            var disciplina = disciplinaRepository.save(DisciplinaFactory.criarDisciplina(professor));

            var aluno = alunoRepository.save(AlunoFactory.criarAluno());

            var esperado = NotaFactory.criarNota(aluno, disciplina);

            var salva = notaRepository.save(esperado);

            assertAll(
                    () -> assertNotNull(salva.getId()),
                    () -> assertEquals(esperado.getValor(), salva.getValor()),
                    () -> assertEquals(esperado.getTipo(), salva.getTipo()),
                    () -> assertEquals(esperado.getBimestre(), salva.getBimestre()),
                    () -> assertEquals(aluno.getId(), salva.getAluno().getId())
            );
        }
    }
}
