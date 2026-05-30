package com.school.api.professores.dto;

import com.school.api.professores.Professor;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados detalhados de um professor")
public record DadosDetalhamentoProfessor(

        @Schema(example = "1")
        Long id,

        @Schema(example = "João Carlos Silva")
        String nome,

        @Schema(example = "joao.silva@gmail.com")
        String email,

        @Schema(example = "(11) 98765-4321")
        String telefone
) {

    public DadosDetalhamentoProfessor(Professor professor){
        this(professor.getId(), professor.getNome(), professor.getEmail(), professor.getTelefone());
    }
}
