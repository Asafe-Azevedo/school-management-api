package com.school.api.support.factory.aluno.nota;

import com.school.api.aluno.Aluno;
import com.school.api.aluno.disciplina.Disciplina;
import com.school.api.aluno.nota.Nota;
import com.school.api.aluno.nota.TipoNota;
import com.school.api.support.util.TestConstants;

public final class NotaFactory {

    private NotaFactory(){

    }

    public static Nota criarNota(Aluno aluno, Disciplina disciplina){
        return new Nota(
                null,
                TestConstants.VALOR_NOTA,
                TestConstants.TIPO_NOTA,
                TestConstants.BIMESTRE,
                aluno,
                disciplina
        );
    }

    public static Nota criarNota(Aluno aluno, Disciplina disciplina, Double valor, TipoNota tipo, Integer bimestre){
        return new Nota(
                null,
                valor,
                tipo,
                bimestre,
                aluno,
                disciplina
        );
    }
}
