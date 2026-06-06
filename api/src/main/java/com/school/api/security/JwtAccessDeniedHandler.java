package com.school.api.security;

import com.school.api.infra.erros.DadosErro;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        DadosErro erro = new DadosErro(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(), "Acesso negado", "Você não possui permissão para acessar este recurso"
        );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        new ObjectMapper().writeValue(response.getOutputStream(), erro);
    }
}
