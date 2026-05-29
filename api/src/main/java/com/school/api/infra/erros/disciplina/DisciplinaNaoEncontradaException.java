package com.school.api.infra.erros.disciplina;

import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.infra.erros.NotFoundException;

public class DisciplinaNaoEncontradaException extends NotFoundException {

    public DisciplinaNaoEncontradaException(){
        super("Discipina não encontrada");
    }
}
