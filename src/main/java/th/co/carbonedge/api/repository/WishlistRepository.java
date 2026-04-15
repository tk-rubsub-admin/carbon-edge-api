package th.co.carbonedge.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import th.co.carbonedge.api.domain.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByCustomerId(String customerId);

    boolean existsByCustomerId(String customerId);
}
