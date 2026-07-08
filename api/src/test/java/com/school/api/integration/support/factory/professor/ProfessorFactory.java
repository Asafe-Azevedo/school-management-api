package com.school.api.integration.support.factory.professor;

import com.school.api.integration.support.util.TestDataGenerator;
import com.school.api.professores.Professor;
import com.school.api.integration.support.factory.EnderecoFactory;
import com.school.api.integration.support.util.TestConstants;

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
                EnderecoFactory.criarEndereco()
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
