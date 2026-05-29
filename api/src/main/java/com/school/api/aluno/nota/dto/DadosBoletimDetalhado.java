package com.school.api.aluno.nota.dto;

import java.util.Map;

public record DadosBoletimDetalhado(
        String disciplina,
        Map<Integer, Double> mediasPorBimestre,
        Double mediaFinal,
        String situacao
) {
}
