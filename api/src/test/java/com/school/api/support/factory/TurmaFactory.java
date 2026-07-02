package com.school.api.support.factory;

import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.support.util.TestConstants;
import com.school.api.support.util.TestDataGenerator;
import com.school.api.turma.Serie;
import com.school.api.turma.Turma;

import java.util.ArrayList;
import java.util.List;

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
