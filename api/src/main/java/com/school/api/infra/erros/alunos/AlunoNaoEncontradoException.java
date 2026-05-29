package com.school.api.infra.erros.alunos;

import com.school.api.infra.erros.NotFoundException;

public class AlunoNaoEncontradoException extends NotFoundException {

    public AlunoNaoEncontradoException(){
        super("Aluno não encontrado");
    }
}
