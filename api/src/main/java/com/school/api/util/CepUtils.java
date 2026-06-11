package com.school.api.util;

import com.school.api.infra.erros.RegraNegocioException;

public class CepUtils {

    public static String formatarCep(String cep){
        cep = cep.replaceAll("\\D", "");

        if(cep.length() != 8){
            throw new RegraNegocioException("CEP inválido");
        } return cep.substring(0, 5) + "-" + cep.substring(5);
    }


}
