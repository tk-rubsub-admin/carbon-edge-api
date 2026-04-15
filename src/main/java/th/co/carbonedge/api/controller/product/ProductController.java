package th.co.carbonedge.api.controller.product;

import lombok.RequiredArgsConstructor;
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
import th.co.carbonedge.api.constant.ProductStatus;
import th.co.carbonedge.api.dto.product.CreateProductRequest;
import th.co.carbonedge.api.dto.product.ProductPageResponse;
import th.co.carbonedge.api.dto.product.ProductResponse;
import th.co.carbonedge.api.dto.product.UpdateProductRequest;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.service.ProductService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ProductPageResponse listProducts(
        @RequestParam(defaultValue = "0") @Min(0) Integer page,
        @RequestParam(defaultValue = "20") @Min(1) Integer size,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) ProductStatus status,
        @RequestParam(required = false) Boolean inStock,
        @RequestParam(required = false) Boolean featured,
        @RequestParam(required = false) String sort
    ) {
        return productService.listProducts(page, size, keyword, categoryId, status, inStock, featured, sort);
    }

    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable @Min(1) Long productId) throws DataNotFoundException {
        return productService.getProductById(productId);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }

    @PutMapping("/{productId}")
    public ProductResponse updateProduct(
        @PathVariable @Min(1) Long productId,
        @Valid @RequestBody UpdateProductRequest request
    ) throws DataNotFoundException {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable @Min(1) Long productId) throws DataNotFoundException {
        productService.deleteProduct(productId);
    }
}
