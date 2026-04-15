package th.co.carbonedge.api.service;

import java.net.URI;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.carbonedge.api.constant.CategoryStatus;
import th.co.carbonedge.api.domain.Category;
import th.co.carbonedge.api.dto.category.CategoryListResponse;
import th.co.carbonedge.api.dto.category.CategoryResponse;
import th.co.carbonedge.api.dto.category.CreateCategoryRequest;
import th.co.carbonedge.api.dto.category.UpdateCategoryRequest;
import th.co.carbonedge.api.exception.DataConflictException;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.repository.CategoryRepository;
import th.co.carbonedge.api.specification.CategorySpecification;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CategoryListResponse listCategories(CategoryStatus status, Long parentId, String keyword) {
        List<th.co.carbonedge.api.dto.category.Category> categories = categoryRepository.findAll(
                CategorySpecification.withFilters(status, parentId, keyword),
                Sort.by(Sort.Order.asc("sortOrder"), Sort.Order.asc("name"))
            )
            .stream()
            .map(this::toDto)
            .toList();

        return new CategoryListResponse().setData(categories);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long categoryId) throws DataNotFoundException {
        return new CategoryResponse().setData(toDto(findActiveCategory(categoryId)));
    }

    public CategoryResponse createCategory(CreateCategoryRequest request) {
        validateSlugUniqueness(request.getSlug(), null);
        Category category = new Category();
        applyCreateRequest(category, request);
        return new CategoryResponse().setData(toDto(categoryRepository.save(category)));
    }

    public CategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest request) throws DataNotFoundException {
        Category category = findActiveCategory(categoryId);
        validateSlugUniqueness(request.getSlug(), categoryId);
        applyUpdateRequest(category, request);
        return new CategoryResponse().setData(toDto(categoryRepository.save(category)));
    }

    public void deleteCategory(Long categoryId) throws DataNotFoundException {
        Category category = findActiveCategory(categoryId);
        category.setStatus(CategoryStatus.ARCHIVED);
        categoryRepository.save(category);
    }

    private Category findActiveCategory(Long categoryId) throws DataNotFoundException {
        return categoryRepository.findByIdAndStatusNot(categoryId, CategoryStatus.ARCHIVED)
            .orElseThrow(() -> new DataNotFoundException("Category " + categoryId + " not found"));
    }

    private void validateSlugUniqueness(String slug, Long currentCategoryId) {
        if (slug == null || slug.isBlank()) {
            return;
        }

        boolean exists = currentCategoryId == null
            ? categoryRepository.existsBySlug(slug)
            : categoryRepository.existsBySlugAndIdNot(slug, currentCategoryId);
        if (exists) {
            throw new DataConflictException("Category slug already exists: " + slug);
        }
    }

    private void applyCreateRequest(Category category, CreateCategoryRequest request) {
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());
        category.setParentId(request.getParentId());
        category.setImageUrl(toStringValue(request.getImageUrl()));
        category.setStatus(request.getStatus());
        category.setSortOrder(request.getSortOrder());
    }

    private void applyUpdateRequest(Category category, UpdateCategoryRequest request) {
        if (request.getName() != null) {
            category.setName(request.getName());
        }
        if (request.getSlug() != null) {
            category.setSlug(request.getSlug());
        }
        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }
        if (request.getParentId() != null) {
            category.setParentId(request.getParentId());
        }
        if (request.getImageUrl() != null) {
            category.setImageUrl(toStringValue(request.getImageUrl()));
        }
        if (request.getStatus() != null) {
            category.setStatus(request.getStatus());
        }
        if (request.getSortOrder() != null) {
            category.setSortOrder(request.getSortOrder());
        }
    }

    private th.co.carbonedge.api.dto.category.Category toDto(Category category) {
        return new th.co.carbonedge.api.dto.category.Category()
            .setId(category.getId())
            .setName(category.getName())
            .setSlug(category.getSlug())
            .setDescription(category.getDescription())
            .setParentId(category.getParentId())
            .setImageUrl(toUri(category.getImageUrl()))
            .setStatus(category.getStatus())
            .setSortOrder(category.getSortOrder())
            .setCreatedAt(category.getCreatedAt())
            .setUpdatedAt(category.getUpdatedAt());
    }

    private String toStringValue(URI value) {
        return value == null ? null : value.toString();
    }

    private URI toUri(String value) {
        return value == null || value.isBlank() ? null : URI.create(value);
    }
}
