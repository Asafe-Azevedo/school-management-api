package com.school.api.aluno.disciplina.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados necessários para cadastro de uma disciplina")
public record DadosCadastroDisciplina(

        @Schema(
                description = "Nome da disciplina",
                example = "Matemática"
        )
        String nome,

        @Schema(
                description = "Identificador do professor responsável pela disciplina",
                example = "1"
        )
        Long professorId
) {
}
