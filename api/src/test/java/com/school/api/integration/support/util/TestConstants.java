package com.school.api.integration.support.util;

import com.school.api.aluno.nota.TipoNota;
import com.school.api.professores.Especialidade;
import com.school.api.turma.Serie;

import java.time.LocalDate;

public final class TestConstants {



    private TestConstants(){

    }
    public static final String NOME_ALUNO = "João da Silva";
    public static final String EMAIL_ALUNO = "joao@email.com";
    public static final String CPF_ALUNO = "123.456.789-00";

    public static final String RESPONSAVEL = "Maria da Silva";
    public static final String TELEFONE_RESPONSAVEL = "(11)99999-9999";

    public static final Serie SERIE = Serie.PRIMEIRO_EM;

    public static final LocalDate NASCIMENTO = LocalDate.of(2010, 5,10);

    public static final String CPF_INEXISTENTE = "999.999.999-99";

    public static final String CEP = "01001-000";
    public static final String RUA = "Praça da Sé";
    public static final String NUMERO = "100";
    public static final String BAIRRO = "Sé";
    public static final String CIDADE = "São Paulo";
    public static final String UF = "SP";

    public static final String NOME_PROFESSOR = "Jorge Vasconcelos";
    public static final String EMAIL_PROFESSOR = "jorge@vasco.com";
    public static final String TELEFONE = "11899999999";

    public static final Especialidade ESPECIALIDADE_PADRAO = Especialidade.MATEMATICA;

    public static final String NOME_DISCIPLINA = "Matemática";

    public static final Double VALOR_NOTA = 8.5;
    public static final TipoNota TIPO_NOTA = TipoNota.PROVA;
    public static final Integer BIMESTRE = 1;

    public static final Long ID_INEXISTENTE = Long.MAX_VALUE;




}
