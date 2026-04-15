package th.co.carbonedge.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import th.co.carbonedge.api.domain.User;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByCustomerId(String customerId);

    boolean existsByCustomerId(String customerId);

    boolean existsByEmail(String email);

    boolean existsByEmailAndCustomerIdNot(String email, String customerId);
}
