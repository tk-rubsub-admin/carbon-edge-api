package th.co.carbonedge.api.specification;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import th.co.carbonedge.api.domain.Product;
import th.co.carbonedge.api.constant.ProductStatus;

public final class ProductSpecifications {

    private ProductSpecifications() {
    }

    public static Specification<Product> withFilters(
        String keyword,
        Long categoryId,
        ProductStatus status,
        Boolean inStock,
        Boolean featured
    ) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (keyword != null && !keyword.isBlank()) {
                String likeValue = "%" + keyword.trim().toLowerCase() + "%";
                predicate = cb.and(predicate, cb.or(
                    cb.like(cb.lower(root.get("name")), likeValue),
                    cb.like(cb.lower(root.get("sku")), likeValue),
                    cb.like(cb.lower(root.get("description")), likeValue)
                ));
            }
            if (categoryId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("categoryId"), categoryId));
            }
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            } else {
                predicate = cb.and(predicate, cb.notEqual(root.get("status"), ProductStatus.ARCHIVED));
            }
            if (inStock != null) {
                predicate = cb.and(predicate, cb.equal(root.get("inStock"), inStock));
            }
            if (featured != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isFeatured"), featured));
            }

            return predicate;
        };
    }
}
