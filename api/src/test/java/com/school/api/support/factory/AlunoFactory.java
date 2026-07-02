package com.school.api.support.factory;

import com.school.api.aluno.Aluno;
import com.school.api.endereco.Endereco;
import com.school.api.support.util.TestDataGenerator;
import com.school.api.support.util.TestConstants;

public final class AlunoFactory {

    private AlunoFactory(){

    }
    public static Aluno criarAluno() {

        int indice = TestDataGenerator.next();
        return new Aluno(
                TestDataGenerator.nome("Aluno", indice),
                TestDataGenerator.email("aluno", indice),
                TestDataGenerator.cpf(indice),
                criarEndereco(),
                TestConstants.NASCIMENTO,
                TestConstants.RESPONSAVEL,
                TestConstants.TELEFONE_RESPONSAVEL,
                TestConstants.SERIE
        );
    }
    public static Aluno criarAlunoInativo(){
        Aluno aluno = criarAluno();
        aluno.excluir();

        return aluno;
    }
    public static Aluno criarAlunoComCpf(String cpf){
        int indice = TestDataGenerator.next();

        return new Aluno(
                TestDataGenerator.nome("Aluno", indice),
                TestDataGenerator.email("aluno", indice),
                cpf,
                criarEndereco(),
                TestConstants.NASCIMENTO,
                TestConstants.RESPONSAVEL,
                TestConstants.TELEFONE_RESPONSAVEL,
                TestConstants.SERIE
        );
    }
    private static Endereco criarEndereco(){
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


}
