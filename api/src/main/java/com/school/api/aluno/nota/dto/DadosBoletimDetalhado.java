package com.school.api.aluno.nota.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "Boletim detalhado do aluno por disciplina")
public record DadosBoletimDetalhado(

        @Schema(
                description = "Nome da disciplina",
                example = "Matemática"
        )
        String disciplina,

        @Schema(
                description = "Médias calculadas por bimestre"
        )
        Map<Integer, Double> mediasPorBimestre,

        @Schema(
                description = "Média final da disciplina",
                example = "7.75"
        )
        Double mediaFinal,

        @Schema(
                description = "Situação final do aluno na disciplina",
                example = "APROVADO"
        )
        String situacao
) {
}
