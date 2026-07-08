package com.school.api.integration.support.scenario.aluno;

import com.school.api.aluno.Aluno;
import com.school.api.aluno.AlunoRepository;
import com.school.api.integration.support.factory.aluno.AlunoFactory;
import com.school.api.integration.support.scenario.turma.TurmaScenario;
import com.school.api.turma.Turma;
import org.springframework.stereotype.Component;

@Component
public class AlunoScenario {

    private final AlunoRepository alunoRepository;
    private final TurmaScenario turmaScenario;

    public AlunoScenario(AlunoRepository alunoRepository, TurmaScenario turmaScenario){
        this.alunoRepository = alunoRepository;
        this.turmaScenario = turmaScenario;
    }

    public Aluno criarAluno(){
        Turma turma = turmaScenario.criarTurmaDisponivel();
        Aluno aluno = AlunoFactory.criarAluno();
            aluno.matricularNaTurma(turma);

        return alunoRepository.save(aluno);
    }

    public Aluno criarAluno(Turma turma){
        Aluno aluno = AlunoFactory.criarAluno();
        aluno.matricularNaTurma(turma);

        return alunoRepository.save(aluno);
    }
}
