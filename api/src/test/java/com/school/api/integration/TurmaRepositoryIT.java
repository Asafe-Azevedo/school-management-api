package com.school.api.integration;


import com.school.api.aluno.AlunoRepository;
import com.school.api.support.factory.AlunoFactory;
import com.school.api.support.factory.TurmaFactory;
import com.school.api.turma.Serie;
import com.school.api.turma.TurmaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TurmaRepositoryIT {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    @DisplayName("Deve retornar turmas ordenadas por quantidade de alunos")
    void deveRetornarTurmasOrdenadasPorQuantidadeDeAlunos(){

        var turmaA = turmaRepository.save(TurmaFactory.criarTurma(Serie.PRIMEIRO_EM));
        var aluno1 = AlunoFactory.criarAluno();
        var aluno2 = AlunoFactory.criarAluno();
            aluno1.matricularNaTurma(turmaA);
            aluno2.matricularNaTurma(turmaA);

            alunoRepository.save(aluno1);
            alunoRepository.save(aluno2);

        var turmaB = turmaRepository.save(TurmaFactory.criarTurma(Serie.PRIMEIRO_EM));
        var aluno3 = AlunoFactory.criarAluno();
            aluno3.matricularNaTurma(turmaB);

            alunoRepository.save(aluno3);

        var turmaC = turmaRepository.save(TurmaFactory.criarTurma(Serie.PRIMEIRO_EM));
        var aluno4 = AlunoFactory.criarAluno();
        var aluno5 = AlunoFactory.criarAluno();
        var aluno6 = AlunoFactory.criarAluno();
            aluno4.matricularNaTurma(turmaC);
            aluno5.matricularNaTurma(turmaC);
            aluno6.matricularNaTurma(turmaC);

            alunoRepository.save(aluno4);
            alunoRepository.save(aluno5);
            alunoRepository.save(aluno6);

        var resultado = turmaRepository.buscarTurmasOrdenadasPorSerieOrdenadas(Serie.PRIMEIRO_EM);

        assertAll(
                () -> assertEquals(turmaB.getId(), resultado.get(0).getId()),
                () -> assertEquals(turmaA.getId(), resultado.get(1).getId()),
                () -> assertEquals(turmaC.getId(), resultado.get(2).getId())
        );
    }

    @Test
    @DisplayName("Não deve retornar turma de outras séries")
    void naoDeveRetornarTurmaDeOutrasSeries(){
        var turma = turmaRepository.save(TurmaFactory.criarTurma(Serie.SEGUNDO_EM));
        var aluno = AlunoFactory.criarAluno();
            aluno.matricularNaTurma(turma);

            alunoRepository.save(aluno);

        var resultado = turmaRepository.buscarTurmasOrdenadasPorSerieOrdenadas(Serie.PRIMEIRO_EM);

        assertTrue(resultado.isEmpty());
    }
}
