package th.co.carbonedge.api.service;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import th.co.carbonedge.api.domain.User;
import th.co.carbonedge.api.exception.InvalidRequestException;

@Service
public class AuthTokenService {

    private static final Base64.Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder BASE64_URL_DECODER = Base64.getUrlDecoder();

    private final ObjectMapper objectMapper;
    private final String tokenSecret;
    private final long tokenTtlMinutes;

    public AuthTokenService(
        ObjectMapper objectMapper,
        @Value("${app.auth.token-secret:carbonedge-local-auth-secret-change-me}") String tokenSecret,
        @Value("${app.auth.token-ttl-minutes:1440}") long tokenTtlMinutes
    ) {
        this.objectMapper = objectMapper;
        this.tokenSecret = tokenSecret;
        this.tokenTtlMinutes = tokenTtlMinutes;
    }

    public AuthToken generateToken(User user) {
        OffsetDateTime issuedAt = OffsetDateTime.now(ZoneOffset.UTC);
        OffsetDateTime expiresAt = issuedAt.plusMinutes(tokenTtlMinutes);

        Map<String, Object> header = Map.of(
            "alg", "HS256",
            "typ", "JWT"
        );

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", user.getId());
        payload.put("userId", user.getId());
        payload.put("customerId", user.getCustomerId());
        payload.put("email", user.getEmail());
        payload.put("status", user.getStatus().name());
        payload.put("iat", issuedAt.toEpochSecond());
        payload.put("exp", expiresAt.toEpochSecond());

        String encodedHeader = encodeJson(header);
        String encodedPayload = encodeJson(payload);
        String unsignedToken = encodedHeader + "." + encodedPayload;
        String signature = sign(unsignedToken);

        return new AuthToken("Bearer", unsignedToken + "." + signature, expiresAt);
    }

    public TokenClaims parseAndValidate(String accessToken) throws InvalidRequestException {
        try {
            String[] tokenParts = accessToken.split("\\.");
            if (tokenParts.length != 3) {
                throw invalidToken();
            }

            String unsignedToken = tokenParts[0] + "." + tokenParts[1];
            String expectedSignature = sign(unsignedToken);
            if (!Objects.equals(expectedSignature, tokenParts[2])) {
                throw invalidToken();
            }

            Map<String, Object> payload = objectMapper.readValue(
                BASE64_URL_DECODER.decode(tokenParts[1]),
                new TypeReference<Map<String, Object>>() {}
            );

            long expiresAtEpochSecond = ((Number) payload.get("exp")).longValue();
            if (OffsetDateTime.ofInstant(java.time.Instant.ofEpochSecond(expiresAtEpochSecond), ZoneOffset.UTC)
                .isBefore(OffsetDateTime.now(ZoneOffset.UTC))) {
                throw new InvalidRequestException("invalid_request", "Access token has expired", org.springframework.http.HttpStatus.UNAUTHORIZED);
            }

            return new TokenClaims(
                String.valueOf(payload.get("userId")),
                String.valueOf(payload.get("customerId")),
                String.valueOf(payload.get("email")),
                OffsetDateTime.ofInstant(Instant.ofEpochSecond(((Number) payload.get("iat")).longValue()), ZoneOffset.UTC)
            );
        } catch (InvalidRequestException exception) {
            throw exception;
        } catch (Exception exception) {
            throw invalidToken();
        }
    }

    private String encodeJson(Map<String, Object> value) {
        try {
            return BASE64_URL_ENCODER.encodeToString(objectMapper.writeValueAsBytes(value));
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Failed to encode auth token payload", exception);
        }
    }

    private String sign(String unsignedToken) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(tokenSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signature = mac.doFinal(unsignedToken.getBytes(StandardCharsets.UTF_8));
            return BASE64_URL_ENCODER.encodeToString(signature);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to sign auth token", exception);
        }
    }

    private InvalidRequestException invalidToken() {
        return new InvalidRequestException("invalid_request", "Access token is invalid", org.springframework.http.HttpStatus.UNAUTHORIZED);
    }

    public record AuthToken(String tokenType, String accessToken, OffsetDateTime expiresAt) {}

    public record TokenClaims(String userId, String customerId, String email, OffsetDateTime issuedAt) {}
}
