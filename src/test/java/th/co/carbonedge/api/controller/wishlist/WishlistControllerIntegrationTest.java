package th.co.carbonedge.api.controller.wishlist;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import th.co.carbonedge.api.constant.ProductStatus;
import th.co.carbonedge.api.domain.Cart;
import th.co.carbonedge.api.domain.Customer;
import th.co.carbonedge.api.domain.Product;
import th.co.carbonedge.api.dto.auth.LoginRequest;
import th.co.carbonedge.api.dto.customer.CreateCustomerRequest;
import th.co.carbonedge.api.dto.wishlist.AddWishlistItemRequest;
import th.co.carbonedge.api.dto.wishlist.MoveWishlistItemToCartRequest;
import th.co.carbonedge.api.notifier.CustomerEmailVerificationNotifier;
import th.co.carbonedge.api.repository.CartRepository;
import th.co.carbonedge.api.repository.ProductRepository;
import th.co.carbonedge.api.repository.WishlistRepository;
import th.co.carbonedge.api.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:wishlist-controller-flow;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.main.allow-bean-definition-overriding=true"
})
class WishlistControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void wishlistFlowShouldCreateAddGetAndRemoveItem() throws Exception {
        String customerId = customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("wishlist.customer@example.com")
                .setPhoneNumber("0834567890")
                .setPassword("StrongPass123!")
                .setFirstName("Wish")
                .setLastName("List")
        ).getData().getId();
        String accessToken = loginAndGetAccessToken("wishlist.customer@example.com", "StrongPass123!");

        Long productId = productRepository.save(Product.builder()
            .sku("WISH-001")
            .name("Wishlist Product")
            .slug("wishlist-product")
            .price(BigDecimal.valueOf(199))
            .currency("THB")
            .stockQuantity(10)
            .status(ProductStatus.ACTIVE)
            .thumbnailUrl("https://example.com/item.png")
            .build()).getId();

        long wishlistId = wishlistRepository.findByCustomerId(customerId)
            .orElseThrow()
            .getId();

        mockMvc.perform(get("/v1/wishlists")
                .param("customerId", customerId)
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(wishlistId))
            .andExpect(jsonPath("$.data.customerId").value(customerId))
            .andExpect(jsonPath("$.data.totalItems").value(0));

        mockMvc.perform(post("/v1/wishlists/{wishlistId}/items", wishlistId)
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new AddWishlistItemRequest().setProductId(productId))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.totalItems").value(1))
            .andExpect(jsonPath("$.data.items[0].productId").value(productId))
            .andExpect(jsonPath("$.data.items[0].productName").value("Wishlist Product"));

        mockMvc.perform(get("/v1/wishlists")
                .param("customerId", customerId)
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.id").value(wishlistId))
            .andExpect(jsonPath("$.data.items[0].sku").value("WISH-001"));

        mockMvc.perform(delete("/v1/wishlists/{wishlistId}/items/{wishlistItemId}", wishlistId, 1)
                .header("Authorization", "Bearer " + accessToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.totalItems").value(0));
    }

    @Test
    void wishlistShouldCheckStatusAndMoveItemToCart() throws Exception {
        String customerId = customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("wishlist.check@example.com")
                .setPhoneNumber("0845678901")
                .setPassword("StrongPass123!")
                .setFirstName("Wish")
                .setLastName("Check")
        ).getData().getId();
        String accessToken = loginAndGetAccessToken("wishlist.check@example.com", "StrongPass123!");

        Long productId = productRepository.save(Product.builder()
            .sku("WISH-002")
            .name("Move Product")
            .slug("move-product")
            .price(BigDecimal.valueOf(299))
            .currency("THB")
            .stockQuantity(5)
            .status(ProductStatus.ACTIVE)
            .thumbnailUrl("https://example.com/move.png")
            .build()).getId();

        long wishlistId = wishlistRepository.findByCustomerId(customerId)
            .orElseThrow()
            .getId();

        mockMvc.perform(post("/v1/wishlists/{wishlistId}/items", wishlistId)
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new AddWishlistItemRequest().setProductId(productId))))
            .andExpect(status().isOk());

        mockMvc.perform(get("/v1/wishlists/check")
                .header("Authorization", "Bearer " + accessToken)
                .param("customerId", customerId)
                .param("productId", String.valueOf(productId)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.wishlisted").value(true))
            .andExpect(jsonPath("$.data.productId").value(productId));

        Cart cart = cartRepository.save(Cart.builder().build());

        mockMvc.perform(post("/v1/wishlists/{wishlistId}/items/{wishlistItemId}/move-to-cart", wishlistId, 1)
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(
                    new MoveWishlistItemToCartRequest()
                        .setCartId(cart.getId())
                        .setQuantity(2)
                )))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.totalItems").value(0));

        mockMvc.perform(get("/v1/wishlists/check")
                .header("Authorization", "Bearer " + accessToken)
                .param("customerId", customerId)
                .param("productId", String.valueOf(productId)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.wishlisted").value(false));
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

        JsonNode loginJson = objectMapper.readTree(loginResponse);
        return loginJson.get("accessToken").asText();
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
