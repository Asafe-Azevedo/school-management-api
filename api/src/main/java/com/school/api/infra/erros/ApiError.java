package com.school.api.infra.erros;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        Integer status,
        String erro
) {
}
