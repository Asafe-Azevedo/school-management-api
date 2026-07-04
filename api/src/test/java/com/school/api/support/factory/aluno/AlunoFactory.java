package com.school.api.support.factory.aluno;

import com.school.api.aluno.Aluno;
import com.school.api.endereco.Endereco;
import com.school.api.support.factory.EnderecoFactory;
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
                EnderecoFactory.criarEndereco(),
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
                EnderecoFactory.criarEndereco(),
                TestConstants.NASCIMENTO,
                TestConstants.RESPONSAVEL,
                TestConstants.TELEFONE_RESPONSAVEL,
                TestConstants.SERIE
        );
    }


}
