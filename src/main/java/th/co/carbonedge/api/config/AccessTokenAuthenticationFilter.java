package th.co.carbonedge.api.config;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import th.co.carbonedge.api.common.ErrorResponseFactory;
import th.co.carbonedge.api.exception.InvalidRequestException;
import th.co.carbonedge.api.service.AuthTokenService;

@Component
public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {

    private static final RequestMatcher PUBLIC_ENDPOINTS = new OrRequestMatcher(
        new AntPathRequestMatcher("/v1/categories", HttpMethod.GET.name()),
        new AntPathRequestMatcher("/v1/products", HttpMethod.GET.name()),
        new AntPathRequestMatcher("/v1/auth/login", HttpMethod.POST.name()),
        new AntPathRequestMatcher("/v1/auth/refresh-access-token", HttpMethod.POST.name())
    );

    private final AuthTokenService authTokenService;
    private final ErrorResponseFactory errorResponseFactory;
    private final ObjectMapper objectMapper;

    public AccessTokenAuthenticationFilter(
        AuthTokenService authTokenService,
        ErrorResponseFactory errorResponseFactory,
        ObjectMapper objectMapper
    ) {
        this.authTokenService = authTokenService;
        this.errorResponseFactory = errorResponseFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod()) || PUBLIC_ENDPOINTS.matches(request);
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            writeUnauthorized(response, "invalid_request", "Access token is required");
            return;
        }

        String accessToken = authorizationHeader.substring(7).trim();
        if (accessToken.isEmpty()) {
            writeUnauthorized(response, "invalid_request", "Access token is required");
            return;
        }

        try {
            AuthTokenService.TokenClaims claims = authTokenService.parseAndValidate(accessToken);
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(claims, accessToken, AuthorityUtils.NO_AUTHORITIES);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (InvalidRequestException exception) {
            SecurityContextHolder.clearContext();
            writeUnauthorized(response, exception.getCode(), exception.getMessage());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private void writeUnauthorized(HttpServletResponse response, String code, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(
            response.getWriter(),
            errorResponseFactory.build(code, message, List.of())
        );
    }
}
