package com.school.api.security.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosTokenJWT(

        @NotBlank
        String token
) {
}
