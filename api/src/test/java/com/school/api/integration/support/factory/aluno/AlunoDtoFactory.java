package com.school.api.integration.support.factory.aluno;

import com.school.api.aluno.dto.DadosCadastroAlunos;
import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.integration.support.util.TestConstants;

public final class AlunoDtoFactory {

    private AlunoDtoFactory(){

    }

    public static DadosCadastroAlunos criarAluno(){
        return new DadosCadastroAlunos(
                TestConstants.NOME_ALUNO,
                TestConstants.EMAIL_ALUNO,
                TestConstants.CPF_INEXISTENTE.replace(".", "").replace("-", ""),
                TestConstants.NASCIMENTO,
                TestConstants.RESPONSAVEL,
                TestConstants.TELEFONE_RESPONSAVEL,
                TestConstants.SERIE,
                new DadosEndereco(
                        TestConstants.CEP,
                        null,
                        TestConstants.NUMERO,
                        null,
                        null,
                        null,
                        null
                )
        );
    }

    public static DadosCadastroAlunos criarAlunoComCpf(String cpf){
        return new DadosCadastroAlunos(
                TestConstants.NOME_ALUNO,
                TestConstants.EMAIL_ALUNO,
                cpf,
                TestConstants.NASCIMENTO,
                TestConstants.RESPONSAVEL,
                TestConstants.TELEFONE_RESPONSAVEL,
                TestConstants.SERIE,
                new DadosEndereco(
                        TestConstants.CEP,
                        null,
                        TestConstants.NUMERO,
                        null,
                        null,
                        null,
                        null
                )
        );
    }
}
