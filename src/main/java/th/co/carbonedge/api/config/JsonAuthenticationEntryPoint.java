package th.co.carbonedge.api.config;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import th.co.carbonedge.api.common.ErrorResponseFactory;

@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ErrorResponseFactory errorResponseFactory;
    private final ObjectMapper objectMapper;

    public JsonAuthenticationEntryPoint(ErrorResponseFactory errorResponseFactory, ObjectMapper objectMapper) {
        this.errorResponseFactory = errorResponseFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(
            response.getWriter(),
            errorResponseFactory.build("invalid_request", "Access token is required", List.of())
        );
    }
}
