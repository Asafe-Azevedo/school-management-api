package com.school.api.integration.support.scenario.turma;

import com.school.api.integration.support.factory.turma.TurmaFactory;
import com.school.api.turma.Serie;
import com.school.api.turma.Turma;
import com.school.api.turma.TurmaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TurmaScenario {

    private final TurmaRepository turmaRepository;

    public TurmaScenario(TurmaRepository turmaRepository){
        this.turmaRepository = turmaRepository;
    }

    public Turma criarTurmaDisponivel(){
        return turmaRepository.save(TurmaFactory.criarTurma(Serie.PRIMEIRO_EM));
    }

    public Turma criarTurma(Serie serie){
        return turmaRepository.save(TurmaFactory.criarTurma(serie));
    }
}
