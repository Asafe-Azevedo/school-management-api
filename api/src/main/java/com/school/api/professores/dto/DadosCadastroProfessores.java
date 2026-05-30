package com.school.api.professores.dto;

import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.professores.Especialidade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Dados necessários para cadastro de um professor")
public record DadosCadastroProfessores(
                                       @NotBlank
                                       @Schema(
                                               description = "Nome completo do professor",
                                               example = "João Carlos Silva"
                                       )
                                       String nome,

                                       @Schema(
                                               description = "Endereço do e-mail do professor",
                                               example = "joao.silva@gmail.com"
                                       )
                                       @NotBlank
                                       @Email(message = "email deve seguir o padrão exemplo@email.com ou exemplo@gmail.com")
                                       String email,

                                       @Schema(
                                               description = "CPF contendo 11 dígitos  numéricos",
                                               example = "11987654321"
                                       )
                                       @NotBlank
                                       @Pattern(regexp = "\\d{10,11}", message = "telefone tem que ter 10 ou 11 digitos")
                                       String telefone,

                                       @Schema(
                                               description = "CPF contendo 11 dígitos numéricos",
                                               example = "12345678901"
                                       )
                                       @NotBlank
                                       @Pattern(regexp = "\\d{11}", message = "cpf deve conter 11 dígitos")
                                       String cpf,

                                       @Schema(
                                               description = "Área de atuação do professor",
                                               example = "MATEMATICA"
                                       )
                                       @NotNull
                                       Especialidade especialidade,

                                       @Schema(
                                               description = "Ddos de endereço do professor"
                                       )
                                       @NotNull
                                       @Valid
                                       DadosEndereco endereco
) {
}
