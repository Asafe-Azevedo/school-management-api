package com.school.api.infra.erros.turmas;

import com.school.api.infra.erros.NotFoundException;

public class TurmaNaoEncontradaException extends NotFoundException {

    public TurmaNaoEncontradaException(){
        super("Turma não encontrada");
    }
}
