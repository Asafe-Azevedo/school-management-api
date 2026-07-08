package com.school.api.integration.support.factory.aluno.nota;

import com.school.api.aluno.nota.TipoNota;
import com.school.api.aluno.nota.dto.DadosCadastroNotas;

public final class NotaDtoFactory {

    private NotaDtoFactory(){

    }

    public static DadosCadastroNotas criarNota(Long alunoId, Long disciplinaId){
        return new DadosCadastroNotas(
                alunoId,
                disciplinaId,
                8.5,
                TipoNota.PROVA,
                1
        );
    }
}
