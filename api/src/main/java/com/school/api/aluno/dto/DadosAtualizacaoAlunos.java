package com.school.api.aluno.dto;

import com.school.api.endereco.dto.DadosEndereco;

public record DadosAtualizacaoAlunos(

        String nome,
        String email,
        String telefoneResponsavel,
        DadosEndereco endereco
) {
}
