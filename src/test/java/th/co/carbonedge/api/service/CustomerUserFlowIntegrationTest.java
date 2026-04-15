package th.co.carbonedge.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;

import th.co.carbonedge.api.constant.CustomerStatus;
import th.co.carbonedge.api.constant.UserStatus;
import th.co.carbonedge.api.domain.Customer;
import th.co.carbonedge.api.dto.customer.CreateCustomerRequest;
import th.co.carbonedge.api.dto.customer.UpdateCustomerRequest;
import th.co.carbonedge.api.notifier.CustomerEmailVerificationNotifier;
import th.co.carbonedge.api.repository.UserRepository;
import th.co.carbonedge.api.repository.WishlistRepository;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:customer-user-flow;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.main.allow-bean-definition-overriding=true"
})
class CustomerUserFlowIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void createCustomerShouldAlsoCreateUserAndWishlist() {
        var response = customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("new.customer@example.com")
                .setPhoneNumber("0812345678")
                .setPassword("StrongPass123!")
                .setFirstName("New")
                .setLastName("Customer")
        );

        var customer = response.getData();
        var user = userRepository.findByCustomerId(customer.getId()).orElseThrow();
        var wishlist = wishlistRepository.findByCustomerId(customer.getId()).orElseThrow();

        assertThat(user.getEmail()).isEqualTo(customer.getEmail());
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING_VERIFICATION);
        assertThat(user.getPasswordHash()).isNotBlank().isNotEqualTo("StrongPass123!");
        assertThat(wishlist.getCustomerId()).isEqualTo(customer.getId());
    }

    @Test
    void updateAndDeleteCustomerShouldSyncUser() throws Exception {
        var response = customerService.createCustomer(
            new CreateCustomerRequest()
                .setEmail("sync.before@example.com")
                .setPhoneNumber("0899999999")
                .setPassword("StrongPass123!")
                .setFirstName("Sync")
                .setLastName("Target")
        );

        String customerId = response.getData().getId();

        customerService.updateCustomer(
            customerId,
            new UpdateCustomerRequest()
                .setEmail("sync.after@example.com")
                .setStatus(CustomerStatus.ACTIVE)
        );

        var syncedUser = userRepository.findByCustomerId(customerId).orElseThrow();
        assertThat(syncedUser.getEmail()).isEqualTo("sync.after@example.com");
        assertThat(syncedUser.getStatus()).isEqualTo(UserStatus.ACTIVE);

        customerService.deleteCustomer(customerId);

        var inactiveUser = userRepository.findByCustomerId(customerId).orElseThrow();
        assertThat(inactiveUser.getStatus()).isEqualTo(UserStatus.INACTIVE);
    }

    @TestConfiguration
    static class TestNotifierConfig {

        @Bean("smtpCustomerEmailVerificationNotifier")
        CustomerEmailVerificationNotifier customerEmailVerificationNotifier() {
            return new CustomerEmailVerificationNotifier() {
                @Override
                public void sendVerificationEmail(Customer customer, String rawToken, OffsetDateTime expiresAt) {
                    // No-op notifier for integration testing.
                }
            };
        }
    }
}
