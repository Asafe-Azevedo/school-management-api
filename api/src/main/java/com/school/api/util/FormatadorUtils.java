package com.school.api.util;

public class FormatadorUtils {

    public static String formatarCpf(String cpf){

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11){
            throw new IllegalArgumentException("CPF inválido");
        }

        return cpf.substring(0, 3) + "." +
               cpf.substring(3, 6) + "." +
               cpf.substring(6, 9) + "-" +
               cpf.substring(9);
    }

    public static String formatarTelefone(String telefone){
        telefone = telefone.replaceAll("\\D", "");

        if (telefone.length() == 11){
            return "(" + telefone.substring(0, 2) + ") " +
                    telefone.substring(2, 7) + "-" +
                    telefone.substring(7);
        }

        if (telefone.length() == 10) {
            return "(" + telefone.substring(0, 2) + ") " +
                    telefone.substring(2, 6) + "-" +
                    telefone.substring(6);
        }
        throw new IllegalArgumentException("Telefone inválido");
    }
}
