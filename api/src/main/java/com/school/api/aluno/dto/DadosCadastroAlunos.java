package com.school.api.aluno.dto;

import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.turma.Serie;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Dados necessários para cadastro de um aluno")
public record DadosCadastroAlunos(

        @Schema(
                description = "Nome completo do aluno",
                example = "Maria Eduarda Silva"
        )
        @NotBlank
        String nome,

        @Schema(
                description = "Endereço de e-mail do aluno",
                example = "maria.silva@gmail.com"
        )
        @NotBlank
        @Email
        String email,

        @Schema(
                description = "CPF contendo 11 dígitos numéricos",
                example = "12345678901"
        )
        @NotBlank
        String cpf,

        @Schema(
                description = "Data de nascimento do aluno",
                example = "2010-05-20"
        )
        @NotNull
        LocalDate dataNascimento,

        @Schema(
                description = "Nome completo do responsável legal",
                example = "Carlos Silva"
        )
        @NotBlank
        String nomeResponsavel,

        @Schema(
                description = "Telefone do responsável",
                example = "110987654321"
        )
        @NotBlank
        String telefoneResponsavel,

        @Schema(
                description = "Série escolar do aluno",
                example = "PRIMEIRO_EM"
        )
        @NotNull
        Serie serie,

        @Schema(
                description = "Dados de endereço do aluno"
        )
        @Valid
        DadosEndereco endereco
) {
}
