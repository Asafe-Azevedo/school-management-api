package com.school.api.integration.support.factory.professor;

import com.school.api.aluno.dto.DadosCadastroAlunos;
import com.school.api.endereco.dto.DadosEndereco;
import com.school.api.integration.support.factory.EnderecoFactory;
import com.school.api.integration.support.util.TestConstants;
import com.school.api.integration.support.util.TestDataGenerator;
import com.school.api.professores.dto.DadosAtualizacaoProfessores;
import com.school.api.professores.dto.DadosCadastroProfessores;

public final class ProfessorDtoFactory {

    private ProfessorDtoFactory(){

    }
        public static DadosCadastroProfessores criarProfessor(){
            int indice = TestDataGenerator.next();

            return new DadosCadastroProfessores(
                    TestDataGenerator.nome("Professor", indice),
                    TestDataGenerator.email("professor", indice),
                    "11999999999",
                    TestDataGenerator.cpf(indice),
                    TestConstants.ESPECIALIDADE_PADRAO,
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

        public static DadosCadastroProfessores criarProfessorComCpf(String cpf){
            return new DadosCadastroProfessores(
                    TestConstants.NOME_PROFESSOR,
                    TestConstants.EMAIL_PROFESSOR,
                    TestConstants.TELEFONE,
                    cpf,
                    TestConstants.ESPECIALIDADE_PADRAO,
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

