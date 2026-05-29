package com.school.api.aluno.nota.dto;

import com.school.api.aluno.nota.Nota;
import com.school.api.professores.dto.DadosDetalhamentoProfessor;

public record DadosDetalhamentoNotas(
        Long id,
        Double valor,
        String tipo,
        Integer bimestre
) {

  public DadosDetalhamentoNotas(Nota nota){
      this(
              nota.getId(),
              nota.getValor(),
              nota.getTipo().name(),
              nota.getBimestre()
      );
  }
}
