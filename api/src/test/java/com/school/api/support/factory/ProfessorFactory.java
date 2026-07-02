package com.school.api.support.factory;

import com.school.api.endereco.Endereco;
import com.school.api.professores.Especialidade;
import com.school.api.professores.Professor;
import com.school.api.support.util.TestConstants;
import com.school.api.support.util.TestDataGenerator;

import java.util.concurrent.atomic.AtomicInteger;

public final class ProfessorFactory {

    private ProfessorFactory(){

    }

    public static Professor criarProfessor(){
        int indice = TestDataGenerator.next();

        return new Professor(
                TestDataGenerator.nome("Professor", indice),
                TestDataGenerator.email("professor", indice),
                TestDataGenerator.telefone(indice),
                TestDataGenerator.cpf(indice),
                TestConstants.ESPECIALIDADE_PADRAO,
                criarEndereco()
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
    public static Professor criarProfessorInativo(){
        Professor professor = criarProfessor();
        professor.excluir();

        return professor;
    }
    private static String gerarCpf(int indice){
        return String.format(
                "%03d.%03d.%03d-%02d",
                indice,
                indice,
                indice,
                indice % 99
        );
    }
    private static String gerarTelefone(int indice){
        return String.format("(11)99999-%04d", indice);
    }
}
