package com.school.api.aluno.dto;

import com.school.api.endereco.dto.DadosEndereco;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para atualização de um aluno")
public record DadosAtualizacaoAlunos(

        @Schema(
                description = "Novo nomde do aluno",
                example = "Maria Eduarda Souza"
        )
        String nome,

        @Schema(
                description = "novo e-mail do aluno",
                example = "maria.souza@gmail.com"
        )
        String email,

        @Schema(
                description = "Novo telefonedo responsável",
                example = "11987654321"
        )
        String telefoneResponsavel,

        @Schema(description = "Novo endereço do aluno")
        DadosEndereco endereco
) {
}
