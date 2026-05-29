package com.school.api.endereco.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosEndereco(

                            @NotBlank(message = "cep deve ser válido")
                            String cep,

                            String logradouro,

                            @NotBlank(message = "numero é obrigatório")
                            String numero,

                            String bairro,

                            String localidade,

                            String uf,
                            String complemento
) {
}
