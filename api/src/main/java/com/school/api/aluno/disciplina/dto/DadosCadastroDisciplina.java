package com.school.api.aluno.disciplina.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados necessários para cadastro de uma disciplina")
public record DadosCadastroDisciplina(

        @Schema(
                description = "Nome da disciplina",
                example = "Matemática"
        )
        @NotBlank
        String nome,

        @Schema(
                description = "Identificador do professor responsável pela disciplina",
                example = "1"
        )
        @NotNull
        Long professorId
) {
}
