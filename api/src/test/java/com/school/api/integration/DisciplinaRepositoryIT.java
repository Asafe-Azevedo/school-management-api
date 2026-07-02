package com.school.api.integration;

import com.school.api.aluno.disciplina.DisciplinaRepository;
import com.school.api.professores.ProfessorRepository;
import com.school.api.support.factory.DisciplinaFactory;
import com.school.api.support.factory.ProfessorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class DisciplinaRepositoryIT {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Test
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
    void naoDeveRetornarDisciplinasInativas(){
        var professor = professorRepository.save(ProfessorFactory.criarProfessor());
        disciplinaRepository.save(DisciplinaFactory.criarDisciplinaInativa(professor));

        var pagina = disciplinaRepository.findAllByAtivoTrue(PageRequest.of(0,10));

        assertAll(
                () -> assertTrue(pagina.isEmpty()),
                () -> assertEquals(0, pagina.getTotalElements())
        );
    }
}
