package com.school.api.turma.dto;

import com.school.api.turma.Serie;

import java.util.List;

public record DadosTurmaDetalhada(
        Long id,
        String nome,
        Serie serie,
        List<String> disciplinas
) {
}
