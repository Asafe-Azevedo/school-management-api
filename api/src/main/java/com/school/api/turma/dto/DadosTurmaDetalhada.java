package com.school.api.turma.dto;

import com.school.api.turma.Serie;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Dados detalhados de uma turma")
public record DadosTurmaDetalhada(

        @Schema(
                description = "Identificador único da turma",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nome da turma",
                example = "Turma A - 1º Ano"
        )
        String nome,

        @Schema(
                description = "Série da turma",
                example = "PRIMEIRO_ANO"
        )
        Serie serie,

        @Schema(
                description = "Lista de disciplinas associadas à turma"
        )
        List<String> disciplinas
) {
}
