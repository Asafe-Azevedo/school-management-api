package com.school.api.aluno.dto;


import com.school.api.aluno.Aluno;

public record DadosListagemAlunos(

        Long id,
        String nome,
        String email,
        String serie,
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
