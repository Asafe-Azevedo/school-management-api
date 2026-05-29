package com.school.api.professores.dto;

import com.school.api.professores.Professor;

public record DadosDetalhamentoProfessor(
        Long id,
        String nome,
        String email,
        String telefone
) {

    public DadosDetalhamentoProfessor(Professor professor){
        this(professor.getId(), professor.getNome(), professor.getEmail(), professor.getTelefone());
    }
}
