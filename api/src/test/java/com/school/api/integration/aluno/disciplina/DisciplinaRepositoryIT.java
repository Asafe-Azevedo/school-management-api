package com.school.api.integration.aluno.disciplina;

import com.school.api.aluno.disciplina.DisciplinaRepository;
import com.school.api.professores.ProfessorRepository;
import com.school.api.support.factory.aluno.disciplina.DisciplinaFactory;
import com.school.api.support.factory.professor.ProfessorFactory;
import com.school.api.support.integration.BaseRepositoryIT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DisciplinaRepository")
class DisciplinaRepositoryIT extends BaseRepositoryIT {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Nested
    @DisplayName("findAllByAtivoTrue")
    class FindAllByAtivoTrue{

        @Test
        @DisplayName("Deve listar somente disciplinas ativas")
        void deveListarSomenteDisciplinasAtivas(){
            var professor = professorRepository.save(ProfessorFactory.criarProfessor());
            disciplinaRepository.save(DisciplinaFactory.criarDisciplina(professor));
            disciplinaRepository.save(DisciplinaFactory.criarDisciplinaInativa(professor));

            var pagina = disciplinaRepository.findAllByAtivoTrue(PageRequest.of(0,10));

            assertAll(
                    () -> assertEquals(1, pagina.getTotalElements()),
                    () -> assertFalse(pagina.isEmpty()),
                    () -> assertTrue(pagina.getContent().get(0).getAtivo())
            );
        }

        @Test
        @DisplayName("Deve retornar página vazia quando não existirem disciplinas ativas")
        void deveRetornarPaginaVaziaQuandoNaoExistiremDisciplinasAtivos(){
            var professor = professorRepository.save(ProfessorFactory.criarProfessor());
            disciplinaRepository.save(DisciplinaFactory.criarDisciplinaInativa(professor));
            var pagina = disciplinaRepository.findAllByAtivoTrue(PageRequest.of(0,10));

            assertAll(
                    () -> assertTrue(pagina.isEmpty()),
                    () -> assertEquals(0, pagina.getTotalElements()),
                    () -> assertEquals(0, pagina.getContent().size())
            );
        }
    }

    @Nested
    @DisplayName("Persistência")
    class PersistenciaIT{

        @Test
        @DisplayName("Deve salvar disciplina")
        void deveSalvarDisciplina(){
            var professor = professorRepository.save(ProfessorFactory.criarProfessor());

            var esperado = DisciplinaFactory.criarDisciplina(professor);

            var salvo = disciplinaRepository.save(esperado);

            assertAll(
                    () -> assertNotNull(salvo.getId()),
                    () -> assertEquals(esperado.getNome(), salvo.getNome()),
                    () -> assertEquals(esperado.getId(), salvo.getProfessor().getId()),
                    () -> assertTrue(salvo.getAtivo())
            );
        }

        @Test
        @DisplayName("Deve realizar exclusão lógica")
        void deveRealizarExclusaoLogica() {

            var professor = professorRepository.save(ProfessorFactory.criarProfessor());

            var disciplina = disciplinaRepository.save(DisciplinaFactory.criarDisciplina(professor));

            disciplina.excluir();

            disciplinaRepository.saveAndFlush(disciplina);

            var atualizado = disciplinaRepository.findById(disciplina.getId()).orElseThrow();

            assertFalse(atualizado.getAtivo());
        }
    }
}
