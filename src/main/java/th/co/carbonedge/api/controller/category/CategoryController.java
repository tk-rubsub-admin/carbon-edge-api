package th.co.carbonedge.api.controller.category;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import th.co.carbonedge.api.constant.CategoryStatus;
import th.co.carbonedge.api.dto.category.CategoryListResponse;
import th.co.carbonedge.api.dto.category.CategoryResponse;
import th.co.carbonedge.api.dto.category.CreateCategoryRequest;
import th.co.carbonedge.api.dto.category.UpdateCategoryRequest;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.service.CategoryService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
@Tag(name = "Categories", description = "Category management APIs")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public CategoryListResponse listCategories(
        @RequestParam(required = false) CategoryStatus status,
        @RequestParam(required = false) Long parentId,
        @RequestParam(required = false) String keyword
    ) {
        return categoryService.listCategories(status, parentId, keyword);
    }

    @GetMapping("/{categoryId}")
    public CategoryResponse getCategoryById(@PathVariable @Min(1) Long categoryId) throws DataNotFoundException {
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }

    @PutMapping("/{categoryId}")
    public CategoryResponse updateCategory(
        @PathVariable @Min(1) Long categoryId,
        @Valid @RequestBody UpdateCategoryRequest request
    ) throws DataNotFoundException {
        return categoryService.updateCategory(categoryId, request);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable @Min(1) Long categoryId) throws DataNotFoundException {
        categoryService.deleteCategory(categoryId);
    }
}
