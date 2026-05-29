package com.school.api.infra.erros;


public class NotFoundException extends RuntimeException{

    public NotFoundException(String mensagem){
        super(mensagem);
    }
}
