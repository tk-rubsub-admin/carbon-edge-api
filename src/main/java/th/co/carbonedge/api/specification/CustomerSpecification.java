package th.co.carbonedge.api.specification;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import th.co.carbonedge.api.constant.CustomerStatus;
import th.co.carbonedge.api.domain.Customer;

public final class CustomerSpecification {

    private CustomerSpecification() {
    }

    public static Specification<Customer> withFilters(
        String keyword,
        CustomerStatus status,
        Boolean isEmailVerified,
        Boolean isPhoneVerified
    ) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (keyword != null && !keyword.isBlank()) {
                String likeValue = "%" + keyword.trim().toLowerCase() + "%";
                predicate = cb.and(predicate, cb.or(
                    cb.like(cb.lower(root.get("email")), likeValue),
                    cb.like(cb.lower(root.get("phoneNumber")), likeValue),
                    cb.like(cb.lower(root.get("firstName")), likeValue),
                    cb.like(cb.lower(root.get("lastName")), likeValue),
                    cb.like(cb.lower(root.get("displayName")), likeValue)
                ));
            }
            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            } else {
                predicate = cb.and(predicate, cb.notEqual(root.get("status"), CustomerStatus.INACTIVE));
            }
            if (isEmailVerified != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isEmailVerified"), isEmailVerified));
            }
            if (isPhoneVerified != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isPhoneVerified"), isPhoneVerified));
            }

            return predicate;
        };
    }
}
