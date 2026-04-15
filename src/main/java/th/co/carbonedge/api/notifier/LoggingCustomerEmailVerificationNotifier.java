package th.co.carbonedge.api.notifier;

import java.time.OffsetDateTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import th.co.carbonedge.api.domain.Customer;

@Slf4j
public class LoggingCustomerEmailVerificationNotifier implements CustomerEmailVerificationNotifier {

    private final String verifyBaseUrl;

    public LoggingCustomerEmailVerificationNotifier(
        @Value("${app.customer.verify-email-url-base:http://localhost:3000/verify-email}") String verifyBaseUrl
    ) {
        this.verifyBaseUrl = verifyBaseUrl;
    }

    @Override
    public void sendVerificationEmail(Customer customer, String rawToken, OffsetDateTime expiresAt) {
        log.info(
            "Customer email verification link for {}: {}?token={} (expiresAt={})",
            customer.getEmail(),
            verifyBaseUrl,
            rawToken,
            expiresAt
        );
    }
}
