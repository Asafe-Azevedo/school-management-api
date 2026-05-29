package com.school.api.professores.dto;

import com.school.api.professores.Professor;
import com.school.api.professores.Especialidade;

public record DadosListagemProfessores(

        Long id,
        String nome,
        String email,
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
