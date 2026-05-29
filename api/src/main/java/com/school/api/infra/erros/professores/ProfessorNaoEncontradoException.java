package com.school.api.infra.erros.professores;

import com.school.api.infra.erros.NotFoundException;

public class ProfessorNaoEncontradoException extends NotFoundException {

    public ProfessorNaoEncontradoException(){
        super("Professor não encontrado");
    }
}
