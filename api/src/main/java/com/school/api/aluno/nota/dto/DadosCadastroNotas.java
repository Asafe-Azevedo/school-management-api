package com.school.api.aluno.nota.dto;

import com.school.api.aluno.nota.TipoNota;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados necessários para lançamento de nota")
public record DadosCadastroNotas(

        @Schema(
                description = "Identificador do aluno",
                example = "1"
        )
        Long alunoId,

        @Schema(
                description = "Identificador da disciplina",
                example = "2"
        )
        Long disciplinaId,

        @Schema(
                description = "Valor da nota entre 0 e 10",
                example = "8.5"
        )
        Double valor,

        @Schema(
                description = "Tipo da avaliação",
                example = "PROVA"
        )
        TipoNota tipo,

        @Schema(
                description = "Bimestre da avaliação",
                example = "2"
        )
        Integer bimestre
) {
}
