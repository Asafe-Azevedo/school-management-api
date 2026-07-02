package com.school.api.integration;

import com.school.api.aluno.AlunoRepository;
import com.school.api.support.factory.AlunoFactory;
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
class AlunoRepositoryIT {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    @DisplayName("Deve retornar true quando CPF existir")
    void deveRetornarTrueQuandoCpfExistir(){
        var aluno = AlunoFactory.criarAluno();
        alunoRepository.save(aluno);

        assertTrue(alunoRepository.existsByCpf(aluno.getCpf()));
    }

    @Test
    @DisplayName("Deve retornar false quando CPF não existir")
    void deveRetornarFalseQuandoCpfNaoExistir(){
        assertFalse(alunoRepository.existsByCpf(TestConstants.CPF_INEXISTENTE));
    }

    @Test
    @DisplayName("Deve listar somente alunos ativos")
    void deveListarSomenteAlunosAtivos(){
        alunoRepository.save(AlunoFactory.criarAluno());
        alunoRepository.save(AlunoFactory.criarAlunoInativo());

        var pagina = alunoRepository.findAllByAtivoTrue(PageRequest.of(0,10));

        assertAll(
                () -> assertEquals(1, pagina.getTotalElements()),
                () -> assertFalse(pagina.isEmpty()),
                () -> assertTrue(pagina.getContent().get(0).getAtivo())
        );
    }
}
