package th.co.carbonedge.api.specification;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import th.co.carbonedge.api.constant.MerchantBusinessType;
import th.co.carbonedge.api.constant.MerchantStatus;
import th.co.carbonedge.api.domain.Merchant;

public final class MerchantSpecification {

    private MerchantSpecification() {
    }

    public static Specification<Merchant> withFilters(
        String keyword,
        MerchantStatus status,
        MerchantBusinessType businessType,
        Boolean isOfficial
    ) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (keyword != null && !keyword.isBlank()) {
                String likeValue = "%" + keyword.trim().toLowerCase() + "%";
                predicate = cb.and(predicate, cb.or(
                    cb.like(cb.lower(root.get("merchantCode")), likeValue),
                    cb.like(cb.lower(root.get("shopName")), likeValue),
                    cb.like(cb.lower(root.get("legalName")), likeValue),
                    cb.like(cb.lower(root.get("email")), likeValue),
                    cb.like(cb.lower(root.get("taxId")), likeValue)
                ));
            }
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            } else {
                predicate = cb.and(predicate, cb.notEqual(root.get("status"), MerchantStatus.INACTIVE));
            }
            if (businessType != null) {
                predicate = cb.and(predicate, cb.equal(root.get("businessType"), businessType));
            }
            if (isOfficial != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isOfficial"), isOfficial));
            }

            return predicate;
        };
    }
}
