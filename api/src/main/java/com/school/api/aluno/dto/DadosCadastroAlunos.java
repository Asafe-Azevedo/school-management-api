package com.school.api.aluno.dto;

import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.turma.Serie;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "DTO para cadastro de alunos")
public record DadosCadastroAlunos(

        @Schema(
                description = "Nome completo do aluno",
                example = "João Figueredo Silva"
        )
        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

        @Schema(
                description = "CPF contendo 11 dígitos",
                example = "12345678901"
        )
        @NotBlank
        String cpf,

        @NotNull
        LocalDate dataNascimento,

        @NotBlank
        String nomeResponsavel,

        @NotBlank
        String telefoneResponsavel,

        @NotNull
        Serie serie,

        @Valid
        DadosEndereco endereco
) {
}
