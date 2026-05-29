package com.school.api.aluno.dto;

import com.school.api.aluno.Aluno;

public record DadosDetalhamentoAluno(

        Long id,
        String nome,
        String email
) {

    public DadosDetalhamentoAluno(Aluno aluno){
        this(aluno.getId(), aluno.getNome(), aluno.getEmail());
    }
}
