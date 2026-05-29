package com.school.api.professores.dto;

import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.professores.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroProfessores(
                                       @NotBlank
                                       String nome,

                                       @NotBlank
                                       @Email(message = "email deve seguir o padrão exemplo@email.com ou exemplo@gmail.com")
                                       String email,

                                       @NotBlank
                                       @Pattern(regexp = "\\d{10,11}", message = "telefone tem que ter 10 ou 11 digitos")
                                       String telefone,

                                       @NotBlank
                                       @Pattern(regexp = "\\d{11}", message = "cpf deve conter 11 dígitos")
                                       String cpf,

                                       @NotNull
                                       Especialidade especialidade,

                                       @NotNull
                                       @Valid
                                       DadosEndereco endereco
) {
}
