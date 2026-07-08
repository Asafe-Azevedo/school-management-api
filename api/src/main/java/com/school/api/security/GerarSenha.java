package com.school.api.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenha {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String senha = "umdois3";

        System.out.println(encoder.encode(senha));
    }
}
