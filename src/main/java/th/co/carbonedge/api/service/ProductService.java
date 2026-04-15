package th.co.carbonedge.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.carbonedge.api.constant.ProductStatus;
import th.co.carbonedge.api.dto.product.CreateProductRequest;
import th.co.carbonedge.api.dto.product.ProductPageResponse;
import th.co.carbonedge.api.dto.product.ProductResponse;

import th.co.carbonedge.api.dto.product.UpdateProductRequest;
import th.co.carbonedge.api.domain.Product;
import th.co.carbonedge.api.exception.DataConflictException;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.mapper.ProductMapper;
import th.co.carbonedge.api.repository.ProductRepository;
import th.co.carbonedge.api.specification.ProductSpecifications;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public ProductPageResponse listProducts(
        Integer page,
        Integer size,
        String keyword,
        Long categoryId,
        ProductStatus status,
        Boolean inStock,
        Boolean featured,
        String sort
    ) {
        Pageable pageable = PageRequest.of(
            page == null ? 0 : page,
            size == null ? 20 : size,
            parseSort(sort)
        );

        Page<Product> result = productRepository.findAll(
            ProductSpecifications.withFilters(
                keyword,
                categoryId,
                status != null ? status : null,
                inStock,
                featured
            ),
            pageable
        );

        return new ProductPageResponse()
            .data(result.getContent().stream().map(productMapper::toDto).toList())
            .page(result.getNumber())
            .size(result.getSize())
            .totalElements(result.getTotalElements())
            .totalPages(result.getTotalPages())
            .hasNext(result.hasNext());
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long productId) throws DataNotFoundException {
        Product product = findActiveProduct(productId);
        return productMapper.toResponse(product);
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        validateUniqueness(request.getSku(), request.getSlug(), null);
        Product product = productMapper.toNewEntity(request);
        return productMapper.toResponse(productRepository.save(product));
    }

    public ProductResponse updateProduct(Long productId, UpdateProductRequest request) throws DataNotFoundException {
        Product product = findActiveProduct(productId);
        validateUniqueness(request.getSku(), request.getSlug(), productId);
        productMapper.applyUpdateRequest(product, request);
        return productMapper.toResponse(productRepository.save(product));
    }

    public void deleteProduct(Long productId) throws DataNotFoundException {
        Product product = findActiveProduct(productId);
        product.setStatus(ProductStatus.ARCHIVED);
        productRepository.save(product);
    }

    private Product findActiveProduct(Long productId) throws DataNotFoundException {
        return productRepository.findByIdAndStatusNot(productId, ProductStatus.ARCHIVED)
            .orElseThrow(() -> new DataNotFoundException("Product " + productId + " not found"));
    }

    private void validateUniqueness(String sku, String slug, Long currentProductId) {
        if (sku != null) {
            boolean exists = currentProductId == null
                ? productRepository.existsBySku(sku)
                : productRepository.existsBySkuAndIdNot(sku, currentProductId);
            if (exists) {
                throw new DataConflictException("SKU already exists: " + sku);
            }
        }

        if (slug != null) {
            boolean exists = currentProductId == null
                ? productRepository.existsBySlug(slug)
                : productRepository.existsBySlugAndIdNot(slug, currentProductId);
            if (exists) {
                throw new DataConflictException("Slug already exists: " + slug);
            }
        }
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }

        String[] parts = sort.split(",", 2);
        String property = parts[0].trim();
        Sort.Direction direction = parts.length > 1
            ? Sort.Direction.fromOptionalString(parts[1].trim()).orElse(Sort.Direction.ASC)
            : Sort.Direction.ASC;

        return Sort.by(direction, property);
    }
}
