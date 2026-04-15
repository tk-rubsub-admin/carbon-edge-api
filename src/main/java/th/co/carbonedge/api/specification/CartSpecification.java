package th.co.carbonedge.api.specification;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import th.co.carbonedge.api.constant.CartStatus;
import th.co.carbonedge.api.domain.Cart;

public final class CartSpecification {

    private CartSpecification() {
    }

    public static Specification<Cart> withFilters(
        String customerId,
        CartStatus status,
        Long productId
    ) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (customerId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("customerId"), customerId));
            }

            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            }

            if (productId != null) {
                query.distinct(true);
                predicate = cb.and(
                    predicate,
                    cb.equal(root.join("items", JoinType.INNER).get("productId"), productId)
                );
            }

            return predicate;
        };
    }
}
