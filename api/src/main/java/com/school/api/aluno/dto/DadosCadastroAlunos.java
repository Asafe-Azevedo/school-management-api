package com.school.api.aluno.dto;

import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.turma.Serie;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroAlunos(

        @NotBlank
        String nome,

        @NotBlank
        @Email
        String email,

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
