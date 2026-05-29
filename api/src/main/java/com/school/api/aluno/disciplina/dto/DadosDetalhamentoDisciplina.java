package com.school.api.aluno.disciplina.dto;

import com.school.api.aluno.disciplina.Disciplina;

public record DadosDetalhamentoDisciplina(
        Long id,
        String nome,
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
