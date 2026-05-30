package com.school.api.aluno.dto;

import com.school.api.aluno.Aluno;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados básicos de um aluno")
public record DadosDetalhamentoAluno(

        @Schema(example = "1")
        Long id,

        @Schema(example = "Maria Eduarda Silva")
        String nome,

        @Schema(example = "maria.silva@gmail.com")
        String email
) {

    public DadosDetalhamentoAluno(Aluno aluno){
        this(aluno.getId(), aluno.getNome(), aluno.getEmail());
    }
}
