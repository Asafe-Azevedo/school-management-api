package com.school.api.aluno.nota;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de avaliação aceitos pelo sistema")
public enum TipoNota {

    PROVA,
    TRABALHO,
    ATIVIDADE

}
