package com.school.api.aluno.nota.dto;

import com.school.api.aluno.nota.Nota;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados da nota registrada")
public record DadosDetalhamentoNotas(

        @Schema(
                description = "Identificador da nota",
                example = "15"
        )
        Long id,

        @Schema(
                description = "Valor registrado",
                example = "9.0"
        )
        Double valor,

        @Schema(
                description = "Tipo da avaliação",
                example = "TRABALHO"
        )
        String tipo,

        @Schema(
                description = "Bimestre da nota",
                example = "1"
        )
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
