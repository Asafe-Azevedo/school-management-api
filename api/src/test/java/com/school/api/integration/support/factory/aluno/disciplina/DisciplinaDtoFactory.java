package com.school.api.integration.support.factory.aluno.disciplina;

import com.school.api.aluno.disciplina.dto.DadosCadastroDisciplina;
import com.school.api.integration.support.util.TestConstants;

public class DisciplinaDtoFactory {

    public static DadosCadastroDisciplina criarDisciplina(Long professorId){
        return new DadosCadastroDisciplina(
                TestConstants.NOME_DISCIPLINA,
                professorId
        );
    }
}
