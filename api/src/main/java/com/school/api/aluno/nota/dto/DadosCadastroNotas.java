package com.school.api.aluno.nota.dto;

import com.school.api.aluno.nota.TipoNota;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Schema(description = "Dados necessários para lançamento de nota")
public record DadosCadastroNotas(

        @NotNull
        @Schema(
                description = "Identificador do aluno",
                example = "1"
        )
        Long alunoId,

        @NotNull
        @Schema(
                description = "Identificador da disciplina",
                example = "2"
        )
        Long disciplinaId,

        @DecimalMin("0.0")
        @DecimalMax("10.0")
        @NotNull
        @Schema(
                description = "Valor da nota entre 0 e 10",
                example = "8.5"
        )
        Double valor,

        @NotNull
        @Schema(
                description = "Tipo da avaliação",
                example = "PROVA"
        )
        TipoNota tipo,

        @Max(4)
        @Min(1)
        @NotNull
        @Schema(
                description = "Bimestre da avaliação",
                example = "2"
        )
        Integer bimestre
) {
}
