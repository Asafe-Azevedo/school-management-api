package com.school.api.aluno.disciplina.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para atualização da disciplina")
public record DadosAtualizacaoDisciplina(

        @Schema(
                description = "Identificador do novo professor responsável",
                example = "2"
        )
        @NotNull
        Long professorId
) {
}
