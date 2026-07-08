package com.school.api.integration.support.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class TestDataGenerator {

    private static final AtomicInteger idSequence = new AtomicInteger(1);


    private TestDataGenerator(){

    }

    public static int next(){
        return idSequence.getAndIncrement();
    }

    public static String nome(String prefixo, int indice){
        return prefixo + " " + indice;
    }

    public static String email(String prefixo, int indice){
        return prefixo.toLowerCase() + indice + "@email.com";
    }

    public static String telefone(int indice){
        return String.format("(11)99999-%04d", indice);
    }

    public static String cpf(int indice){
        return String.format("%011d", indice);
    }

}

