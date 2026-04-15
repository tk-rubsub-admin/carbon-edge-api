package th.co.carbonedge.api.controller.auth;

import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import th.co.carbonedge.api.dto.auth.AuthenticatedUserResponse;
import th.co.carbonedge.api.dto.auth.LoginRequest;
import th.co.carbonedge.api.dto.auth.LoginResponse;
import th.co.carbonedge.api.dto.auth.RefreshAccessTokenRequest;
import th.co.carbonedge.api.exception.BaseCheckedException;
import th.co.carbonedge.api.service.AuthService;
import th.co.carbonedge.api.service.AuthTokenService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Authentication APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) throws BaseCheckedException {
        return authService.login(request);
    }

    @PostMapping("/refresh-access-token")
    public LoginResponse refreshAccessToken(@Valid @RequestBody RefreshAccessTokenRequest request) throws BaseCheckedException {
        return authService.refreshAccessToken(request);
    }

    @GetMapping("/authenticated-user")
    public AuthenticatedUserResponse getAuthenticatedUser(Authentication authentication) throws BaseCheckedException {
        return authService.getAuthenticatedUser((AuthTokenService.TokenClaims) authentication.getPrincipal());
    }
}
