package com.school.api.professores.dto;

import com.school.api.endereco.dto.DadosEndereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para atualização de professor")
public record DadosAtualizacaoProfessores( @NotNull
                                           Long id,

                                           @Schema(
                                                   description = "Novo telefone do professor",
                                                   example = "João Carlos Silva"
                                           )
                                           String nome,

                                           @Schema(
                                                   description = "Novo telefone do professor",
                                                   example = "11987654321"
                                           )
                                           String telefone,

                                           @Schema(
                                                   description = "Novo endereço do professor"
                                           )
                                           DadosEndereco endereco)
{

}
