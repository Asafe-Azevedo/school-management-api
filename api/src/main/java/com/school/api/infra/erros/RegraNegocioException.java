package com.school.api.infra.erros;

import java.io.IOException;

public class RegraNegocioException extends RuntimeException{

    public RegraNegocioException(String mensagem){
        super(mensagem);
    }
}
