package th.co.carbonedge.api.notifier;

import java.time.OffsetDateTime;

import th.co.carbonedge.api.domain.Customer;

public interface CustomerEmailVerificationNotifier {

    void sendVerificationEmail(Customer customer, String rawToken, OffsetDateTime expiresAt);
}
