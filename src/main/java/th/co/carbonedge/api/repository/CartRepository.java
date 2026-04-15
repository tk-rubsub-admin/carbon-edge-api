package th.co.carbonedge.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import th.co.carbonedge.api.constant.CartStatus;
import th.co.carbonedge.api.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByCustomerId(String customerId);

    List<Cart> findAllByCustomerIdAndStatus(String customerId, CartStatus status);

    Optional<Cart> findByCustomerIdAndStatus(String customerId, CartStatus status);
}
