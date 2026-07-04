package com.school.api.support.factory.aluno.disciplina;

import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.professores.Professor;
import com.school.api.support.util.TestConstants;
import com.school.api.support.util.TestDataGenerator;

public final class DisciplinaFactory {

    private DisciplinaFactory(){

    }

    public static Disciplina criarDisciplina(Professor professor){
        int indice = TestDataGenerator.next();

        return new Disciplina(TestConstants.NOME_DISCIPLINA + " " + indice, professor);
    }
    public static Disciplina criarDisciplinaInativa(Professor professor){
        Disciplina disciplina = criarDisciplina(professor);

        disciplina.excluir();

        return disciplina;
    }
}
