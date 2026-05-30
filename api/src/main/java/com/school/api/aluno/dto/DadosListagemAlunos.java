package com.school.api.aluno.dto;


import com.school.api.aluno.Aluno;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados resumidos para listagem de alunos")
public record DadosListagemAlunos(

        @Schema(example = "1")
        Long id,

        @Schema(example = "Maria Eduarda Silva")
        String nome,

        @Schema(example = "maria.souza@gmail.com")
        String email,

        @Schema(example = "PRIMEIRO_EM")
        String serie,

        @Schema(example = "Carlos Silva")
        String nomeResponsavel
) {
    public DadosListagemAlunos(Aluno aluno) {
        this(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getSerie().name(),
                aluno.getNomeResponsavel()
        );
    }

}
