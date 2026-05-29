package com.school.api.aluno.nota.dto;

import com.school.api.aluno.nota.TipoNota;

public record DadosCadastroNotas(
        Long alunoId,
        Long disciplinaId,
        Double valor,
        TipoNota tipo,

        Integer bimestre
) {
}
