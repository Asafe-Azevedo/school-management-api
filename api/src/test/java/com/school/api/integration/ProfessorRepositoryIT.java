package com.school.api.integration;

import com.school.api.support.factory.ProfessorFactory;
import com.school.api.professores.ProfessorRepository;
import com.school.api.support.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProfessorRepositoryIT {

    @Autowired
    private ProfessorRepository professorRepository;

    @Test
    @DisplayName("Deve retornar true quando CPF existir")
    void deveRetornarTrueQuandoCpfExistir(){
        var professor = ProfessorFactory.criarProfessor();
        professorRepository.save(professor);

        assertTrue(professorRepository.existsByCpf(professor.getCpf()));
    }

    @Test
    @DisplayName("Deve retornar false quando CPF não exisitr")
    void deveRetornarFalseQuandoCpfNaoExistir(){
        assertFalse(professorRepository.existsByCpf(TestConstants.CPF_INEXISTENTE));
    }

    @Test
    @DisplayName("Deve listar somente professores ativos")
    void deveListarSomenteProfessoresAtivos(){
        professorRepository.save(ProfessorFactory.criarProfessor());

        professorRepository.save(ProfessorFactory.criarProfessorInativo());

        var pagina = professorRepository.findAllByAtivoTrue(PageRequest.of(0, 10));

        assertAll(
                () -> assertEquals(1, pagina.getTotalElements()),
                () -> assertFalse(pagina.isEmpty()),
                () -> assertTrue(pagina.getContent().get(0).getAtivo())
        );

    }
}
