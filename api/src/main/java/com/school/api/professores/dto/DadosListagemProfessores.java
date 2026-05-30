package com.school.api.professores.dto;

import com.school.api.professores.Professor;
import com.school.api.professores.Especialidade;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados resumidos de um professor")
public record DadosListagemProfessores(

        @Schema(example = "1")
        Long id,

        @Schema(example = "João Carlos Silva")
        String nome,

        @Schema(example = "joao.silva@gmail.com")
        String email,

        @Schema(example = "MATEMATICA")
        Especialidade especialidade
) {

    public DadosListagemProfessores(Professor professor){
        this(professor.getId(),
                professor.getNome(),
                professor.getEmail(),
                professor.getEspecialidade()
                );
    }
}
