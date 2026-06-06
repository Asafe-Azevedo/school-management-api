package com.school.api.infra.erros;

import java.time.LocalDateTime;

public record DadosErro(

        LocalDateTime timestamp,
        Integer status,
        String erro,
        String mensagem
) {
}
