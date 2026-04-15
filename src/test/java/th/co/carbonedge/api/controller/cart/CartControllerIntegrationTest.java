package th.co.carbonedge.api.controller.cart;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import th.co.carbonedge.api.dto.cart.CreateCartRequest;
import th.co.carbonedge.api.dto.customer.CreateCustomerRequest;
import th.co.carbonedge.api.notifier.CustomerEmailVerificationNotifier;
import th.co.carbonedge.api.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:cart-controller-flow;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.main.allow-bean-definition-overriding=true"
})
class CartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    @Test
    void customerShouldHaveOnlyOneActiveCart() throws Exception {
        String customerId = customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("cart.single@example.com")
                .setPhoneNumber("0861234567")
                .setPassword("StrongPass123!")
                .setFirstName("Cart")
                .setLastName("Single")
        ).getData().getId();
        String accessToken = loginAndGetAccessToken("cart.single@example.com", "StrongPass123!");

        mockMvc.perform(post("/v1/carts")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new CreateCartRequest().setCustomerId(customerId))))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.customerId").value(customerId))
            .andExpect(jsonPath("$.data.status").value("ACTIVE"));

        mockMvc.perform(post("/v1/carts")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new CreateCartRequest().setCustomerId(customerId))))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value("Customer " + customerId + " already has an active cart"));
    }

    @Test
    void shouldGetActiveCartByCustomerId() throws Exception {
        String customerId = customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("cart.active@example.com")
                .setPhoneNumber("0871234567")
                .setPassword("StrongPass123!")
                .setFirstName("Cart")
                .setLastName("Active")
        ).getData().getId();
        String accessToken = loginAndGetAccessToken("cart.active@example.com", "StrongPass123!");

        String createCartResponse = mockMvc.perform(post("/v1/carts")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new CreateCartRequest().setCustomerId(customerId))))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

        long cartId = objectMapper.readTree(createCartResponse).get("data").get("id").asLong();

        mockMvc.perform(get("/v1/carts/active")
                .header("Authorization", "Bearer " + accessToken)
                .param("customerId", customerId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(cartId))
            .andExpect(jsonPath("$.data.customerId").value(customerId))
            .andExpect(jsonPath("$.data.status").value("ACTIVE"));
    }

    private String loginAndGetAccessToken(String email, String password) throws Exception {
        String loginResponse = mockMvc.perform(post("/v1/auth/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                    new LoginRequest()
                        .setEmail(email)
                        .setPassword(password)
                )))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        return objectMapper.readTree(loginResponse).get("accessToken").asText();
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
