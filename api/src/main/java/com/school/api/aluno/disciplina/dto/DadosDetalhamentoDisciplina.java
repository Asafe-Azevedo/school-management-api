package com.school.api.aluno.disciplina.dto;

import com.school.api.aluno.disciplina.Disciplina;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados da disciplina cadastrada")
public record DadosDetalhamentoDisciplina(

        @Schema(example = "1")
        Long id,

        @Schema(example = "Matemática")
        String nome,

        @Schema(example = "Carlos Eduardo")
        String professor
) {

    public DadosDetalhamentoDisciplina(Disciplina disciplina){
        this(
                disciplina.getId(),
                disciplina.getNome(),
                disciplina.getProfessor().getNome()
        );
    }
}
