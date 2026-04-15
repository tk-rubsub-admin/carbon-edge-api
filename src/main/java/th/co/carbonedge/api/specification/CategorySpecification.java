package th.co.carbonedge.api.specification;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import th.co.carbonedge.api.constant.CategoryStatus;
import th.co.carbonedge.api.domain.Category;

public final class CategorySpecification {

    private CategorySpecification() {
    }

    public static Specification<Category> withFilters(
        CategoryStatus status,
        Long parentId,
        String keyword
    ) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (status != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), status));
            } else {
                predicate = cb.and(predicate, cb.notEqual(root.get("status"), CategoryStatus.ARCHIVED));
            }

            if (parentId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("parentId"), parentId));
            }

            if (keyword != null && !keyword.isBlank()) {
                String likeValue = "%" + keyword.trim().toLowerCase() + "%";
                predicate = cb.and(predicate, cb.or(
                    cb.like(cb.lower(root.get("name")), likeValue),
                    cb.like(cb.lower(root.get("slug")), likeValue),
                    cb.like(cb.lower(root.get("description")), likeValue)
                ));
            }

            return predicate;
        };
    }
}
