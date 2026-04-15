package th.co.carbonedge.api.controller.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import th.co.carbonedge.api.domain.Customer;
import th.co.carbonedge.api.dto.auth.LoginRequest;
import th.co.carbonedge.api.dto.auth.RefreshAccessTokenRequest;
import th.co.carbonedge.api.dto.customer.CreateCustomerRequest;
import th.co.carbonedge.api.notifier.CustomerEmailVerificationNotifier;
import th.co.carbonedge.api.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:auth-controller-flow;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.main.allow-bean-definition-overriding=true"
})
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    @Test
    void loginShouldReturnUserAndCustomer() throws Exception {
        customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("auth.login@example.com")
                .setPhoneNumber("0801234567")
                .setPassword("StrongPass123!")
                .setFirstName("Auth")
                .setLastName("Login")
        );

        mockMvc.perform(post("/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                    new LoginRequest()
                        .setEmail("auth.login@example.com")
                        .setPassword("StrongPass123!")
                )))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tokenType").value("Bearer"))
            .andExpect(jsonPath("$.accessToken").isNotEmpty())
            .andExpect(jsonPath("$.expiresAt").isNotEmpty())
            .andExpect(jsonPath("$.data.email").value("auth.login@example.com"))
            .andExpect(jsonPath("$.data.userId").isNotEmpty())
            .andExpect(jsonPath("$.data.wishlistId").isNotEmpty())
            .andExpect(jsonPath("$.data.customer.id").isNotEmpty())
            .andExpect(jsonPath("$.data.customer.email").value("auth.login@example.com"));
    }

    @Test
    void loginShouldRejectInvalidPassword() throws Exception {
        customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("auth.invalid@example.com")
                .setPhoneNumber("0807654321")
                .setPassword("StrongPass123!")
                .setFirstName("Auth")
                .setLastName("Invalid")
        );

        mockMvc.perform(post("/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                    new LoginRequest()
                        .setEmail("auth.invalid@example.com")
                        .setPassword("WrongPassword!")
                )))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("Invalid email or password"));
    }

    @Test
    void refreshAccessTokenShouldReturnNewToken() throws Exception {
        customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("auth.refresh@example.com")
                .setPhoneNumber("0823456789")
                .setPassword("StrongPass123!")
                .setFirstName("Auth")
                .setLastName("Refresh")
        );

        String loginResponse = mockMvc.perform(post("/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                    new LoginRequest()
                        .setEmail("auth.refresh@example.com")
                        .setPassword("StrongPass123!")
                )))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        String accessToken = objectMapper.readTree(loginResponse).get("accessToken").asText();

        mockMvc.perform(post("/v1/auth/refresh-access-token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                    new RefreshAccessTokenRequest().setAccessToken(accessToken)
                )))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tokenType").value("Bearer"))
            .andExpect(jsonPath("$.accessToken").isNotEmpty())
            .andExpect(jsonPath("$.expiresAt").isNotEmpty())
            .andExpect(jsonPath("$.data.email").value("auth.refresh@example.com"));
    }

    @Test
    void refreshAccessTokenShouldRejectInvalidToken() throws Exception {
        mockMvc.perform(post("/v1/auth/refresh-access-token")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                    new RefreshAccessTokenRequest().setAccessToken("invalid.token.value")
                )))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("Access token is invalid"));
    }

    @Test
    void protectedEndpointShouldRejectMissingAccessToken() throws Exception {
        mockMvc.perform(get("/v1/categories/1"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.message").value("Access token is required"));
    }

    @Test
    void protectedEndpointShouldAllowValidAccessToken() throws Exception {
        String customerId = customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("auth.protected@example.com")
                .setPhoneNumber("0891234567")
                .setPassword("StrongPass123!")
                .setFirstName("Auth")
                .setLastName("Protected")
        ).getData().getId();

        String loginResponse = mockMvc.perform(post("/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                    new LoginRequest()
                        .setEmail("auth.protected@example.com")
                        .setPassword("StrongPass123!")
                )))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        String accessToken = objectMapper.readTree(loginResponse).get("accessToken").asText();

        mockMvc.perform(get("/v1/wishlists")
                .param("customerId", customerId)
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.customerId").value(customerId));
    }

    @Test
    void getAuthenticatedUserShouldReturnAuthenticatedUser() throws Exception {
        String customerId = customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("auth.me@example.com")
                .setPhoneNumber("0881234567")
                .setPassword("StrongPass123!")
                .setFirstName("Auth")
                .setLastName("Me")
        ).getData().getId();

        String loginResponse = mockMvc.perform(post("/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                    new LoginRequest()
                        .setEmail("auth.me@example.com")
                        .setPassword("StrongPass123!")
                )))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        String accessToken = objectMapper.readTree(loginResponse).get("accessToken").asText();

        mockMvc.perform(get("/v1/auth/authenticated-user")
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.userId").isNotEmpty())
            .andExpect(jsonPath("$.data.customerId").value(customerId))
            .andExpect(jsonPath("$.data.wishlistId").isNotEmpty())
            .andExpect(jsonPath("$.data.email").value("auth.me@example.com"))
            .andExpect(jsonPath("$.data.lastLoginAt").isNotEmpty())
            .andExpect(jsonPath("$.data.customer.id").value(customerId));
    }

    @TestConfiguration
    static class TestNotifierConfig {

        @Bean("smtpCustomerEmailVerificationNotifier")
        CustomerEmailVerificationNotifier customerEmailVerificationNotifier() {
            return new CustomerEmailVerificationNotifier() {
                @Override
                public void sendVerificationEmail(Customer customer, String rawToken, java.time.OffsetDateTime expiresAt) {
                    // No-op notifier for integration testing.
                }
            };
        }
    }
}
