package com.school.api.support.factory;

import com.school.api.endereco.Endereco;
import com.school.api.support.util.TestConstants;

public final class EnderecoFactory {

    private EnderecoFactory() {

    }

    public static Endereco criarEndereco() {
        return new Endereco(
                TestConstants.CEP,
                TestConstants.RUA,
                TestConstants.NUMERO,
                TestConstants.BAIRRO,
                TestConstants.CIDADE,
                TestConstants.UF,
                null
        );
    }

    public static Endereco criarEnderecoAtualizado() {
        return new Endereco(
                "99999-999",
                "Rua Atualizada",
                "999",
                "Centro",
                "Campinas",
                "SP",
                "Apartamento 101"
        );
    }
}
