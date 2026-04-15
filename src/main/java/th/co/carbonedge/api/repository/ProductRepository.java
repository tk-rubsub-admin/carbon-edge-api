package th.co.carbonedge.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import th.co.carbonedge.api.constant.ProductStatus;
import th.co.carbonedge.api.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    boolean existsBySku(String sku);

    boolean existsBySlug(String slug);

    boolean existsBySkuAndIdNot(String sku, Long id);

    boolean existsBySlugAndIdNot(String slug, Long id);

    Optional<Product> findByIdAndStatusNot(Long id, ProductStatus status);
}
