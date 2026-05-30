package com.school.api.security.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(

        @NotBlank
        String login,

        @NotBlank
        String senha
) {
}
