package com.school.api.integration.support.factory.turma;

import com.school.api.integration.support.util.TestDataGenerator;
import com.school.api.turma.Serie;
import com.school.api.turma.Turma;

import java.util.ArrayList;

public final class TurmaFactory {

    private TurmaFactory(){

    }

    public static Turma criarTurma(Serie serie){
        int indice = TestDataGenerator.next();

        return new Turma(
                null,
                "Turma " + indice,
                30,
                serie,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
