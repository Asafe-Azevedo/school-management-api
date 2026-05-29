package com.school.api.util;

public class CepUtils {

    public static String formatarCep(String cep){
        cep = cep.replaceAll("\\D", "");

        if(cep.length() != 8){
            throw new IllegalArgumentException("CEP inválido");
        } return cep.substring(0, 5) + "-" + cep.substring(5);
    }


}
