package com.school.api.endereco.dto;

public record DtoViaCepResponse(

        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf
) {
}
