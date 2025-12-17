package com.pragma.powerup.infrastructure.out.security.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.powerup.infrastructure.input.rest.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ServiceAuthenticationFilter extends OncePerRequestFilter {

    @Value("${service.secret.plazoleta}")
    private String expectedServiceSecret;

    private static final String SERVICE_SECRET_HEADER = "X-Service-Secret";
    private static final String API_PATH = "/api/v1/";

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        if (!request.getRequestURI().contains(API_PATH)) {
            filterChain.doFilter(request, response);
            return;
        }

        String providedSecret = request.getHeader(SERVICE_SECRET_HEADER);

        if (providedSecret == null || !providedSecret.equals(expectedServiceSecret)) {
            CustomResponse<Void> result = CustomResponse.<Void>builder()
                    .status(HttpStatus.FORBIDDEN.value())
                    .error("Acceso denegado: secreto de servicio inválido o ausente.")
                    .message("Acceso denegado: secreto de servicio inválido o ausente.")
                    .build();

            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write(objectMapper.writeValueAsString(result));

            return;
        }

        filterChain.doFilter(request, response);
    }

}
