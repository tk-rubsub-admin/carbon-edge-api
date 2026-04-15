package th.co.carbonedge.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import th.co.carbonedge.api.domain.CustomerEmailVerificationToken;

public interface CustomerEmailVerificationTokenRepository extends JpaRepository<CustomerEmailVerificationToken, Long> {

    Optional<CustomerEmailVerificationToken> findByTokenHashAndUsedAtIsNull(String tokenHash);

    List<CustomerEmailVerificationToken> findAllByCustomerIdAndUsedAtIsNull(String customerId);
}
