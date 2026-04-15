package th.co.carbonedge.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import th.co.carbonedge.api.constant.MerchantStatus;
import th.co.carbonedge.api.domain.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, String>, JpaSpecificationExecutor<Merchant> {

    boolean existsByMerchantCode(String merchantCode);

    boolean existsByShopSlug(String shopSlug);

    boolean existsByEmail(String email);

    boolean existsByTaxId(String taxId);

    boolean existsByMerchantCodeAndIdNot(String merchantCode, String id);

    boolean existsByShopSlugAndIdNot(String shopSlug, String id);

    boolean existsByEmailAndIdNot(String email, String id);

    boolean existsByTaxIdAndIdNot(String taxId, String id);

    Optional<Merchant> findByIdAndStatusNot(String id, MerchantStatus status);
}
