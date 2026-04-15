package th.co.carbonedge.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import th.co.carbonedge.api.constant.CustomerStatus;
import th.co.carbonedge.api.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndIdNot(String email, String id);

    boolean existsByPhoneNumberAndIdNot(String phoneNumber, String id);

    Optional<Customer> findByIdAndStatusNot(String id, CustomerStatus status);
}
