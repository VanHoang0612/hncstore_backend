package com.hoang.hncstore_backend.core.security.jwt;

import com.hoang.hncstore_backend.core.hepler.Translator;
import com.hoang.hncstore_backend.core.response.ApiResponse;
import com.hoang.hncstore_backend.iam.enums.AuthCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final Translator translator;

    @Override
    public void commence(@NonNull HttpServletRequest request,
                         HttpServletResponse response,
                         @NonNull AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ApiResponse<?> apiResponse = ApiResponse.failure(AuthCode.UNAUTHORIZED,
                translator.getMessage(AuthCode.UNAUTHORIZED, null), null);
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
