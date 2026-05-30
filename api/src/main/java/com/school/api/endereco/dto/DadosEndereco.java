package com.school.api.endereco.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;

@Schema(description = "Dados de endereço")
public record DadosEndereco(

                            @NotBlank(message = "cep deve ser válido")
                            @Schema(
                                    description = "CEP do endereço. Pode ser informado com ou sem hífen. Os demais dados serão preenchidos automaticamente via ViaCEP",
                                    example = "01001000"
                            )
                            String cep,

                            @Schema(
                                    description = "Logradouro retornado automaticamente pelo ViaCEP",
                                    example = "Praça da Sé"
                            )
                            String logradouro,

                            @Schema(
                                    description = "Número do imóvel",
                                    example = "100"
                            )
                            @NotBlank(message = "numero é obrigatório")
                            String numero,

                            @Schema(
                                    description = "Bairro retornado automaticamente pelo ViaCEP",
                                    example = "Sé"
                            )
                            String bairro,

                            @Schema(
                                    description = "Cidade retornada automaticamente pelo ViaCEP",
                                    example = "São Paulo"
                            )
                            String localidade,

                            @Schema(
                                    description = "Unidade federeativa retornada automaticamente pelo ViaCEP",
                                    example = "SP"
                            )
                            String uf,

                            @Schema(
                                    description = "Complemento do endereço",
                                    example = "Apartamento 12"
                            )
                            String complemento
) {
}
