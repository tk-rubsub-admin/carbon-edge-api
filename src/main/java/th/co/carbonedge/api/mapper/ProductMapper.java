package th.co.carbonedge.api.mapper;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import th.co.carbonedge.api.domain.Product;
import th.co.carbonedge.api.domain.ProductImage;
import th.co.carbonedge.api.dto.product.CreateProductRequest;
import th.co.carbonedge.api.dto.product.ProductResponse;
import th.co.carbonedge.api.dto.product.UpdateProductRequest;

@Component
public class ProductMapper {

    public Product toNewEntity(CreateProductRequest request) {
        Product product = new Product();
        applyCreateRequest(product, request);
        return product;
    }

    public void applyCreateRequest(Product product, CreateProductRequest request) {
        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setShortDescription(request.getShortDescription());
        product.setDescription(request.getDescription());
        product.setBrand(request.getBrand());
        product.setCategoryId(request.getCategoryId());
        product.setMerchantId(request.getMerchantId());
        product.setPrice(toBigDecimal(request.getPrice()));
        product.setCurrency(request.getCurrency());
        product.setStockQuantity(request.getStockQuantity());
        product.setStatus(request.getStatus());
        product.setIsFeatured(Boolean.TRUE.equals(request.getIsFeatured()));
        product.setThumbnailUrl(toStringValue(request.getThumbnailUrl()));
        product.setImages(toEmbeddables(request.getImages()));
        product.setTags(copyTags(request.getTags()));
    }

    public void applyUpdateRequest(Product product, UpdateProductRequest request) {
        if (request.getSku() != null) {
            product.setSku(request.getSku());
        }
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getSlug() != null) {
            product.setSlug(request.getSlug());
        }
        if (request.getShortDescription() != null) {
            product.setShortDescription(request.getShortDescription());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if (request.getBrand() != null) {
            product.setBrand(request.getBrand());
        }
        if (request.getCategoryId() != null) {
            product.setCategoryId(request.getCategoryId());
        }
        if (request.getPrice() != null) {
            product.setPrice(toBigDecimal(request.getPrice()));
        }
        if (request.getCurrency() != null) {
            product.setCurrency(request.getCurrency());
        }
        if (request.getStockQuantity() != null) {
            product.setStockQuantity(request.getStockQuantity());
        }
        if (request.getStatus() != null) {
            product.setStatus(request.getStatus());
        }
        if (request.getIsFeatured() != null) {
            product.setIsFeatured(request.getIsFeatured());
        }
        if (request.getThumbnailUrl() != null) {
            product.setThumbnailUrl(toStringValue(request.getThumbnailUrl()));
        }
        if (request.getImages() != null) {
            product.setImages(toEmbeddables(request.getImages()));
        }
        if (request.getTags() != null) {
            product.setTags(copyTags(request.getTags()));
        }
    }

    public ProductResponse toResponse(Product entity) {
        return new ProductResponse().data(
            new th.co.carbonedge.api.dto.product.Product()
                .id(entity.getId())
                .sku(entity.getSku())
                .name(entity.getName())
                .slug(entity.getSlug())
                .shortDescription(entity.getShortDescription())
                .description(entity.getDescription())
                .brand(entity.getBrand())
                .categoryId(entity.getCategoryId())
                .price(entity.getPrice() != null ? entity.getPrice().doubleValue() : null)
                .currency(entity.getCurrency())
                .stockQuantity(entity.getStockQuantity())
                .inStock(entity.getInStock())
                .status(entity.getStatus())
                .isFeatured(entity.getIsFeatured())
                .thumbnailUrl(toUri(entity.getThumbnailUrl()))
                .images(toDtoImages(entity.getImages()))
                .tags(copyTags(entity.getTags()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
        );
    }

    public th.co.carbonedge.api.dto.product.Product toDto(Product entity) {
        return toResponse(entity).getData();
    }

    private List<ProductImage> toEmbeddables(List<th.co.carbonedge.api.dto.product.ProductImage> images) {
        if (images == null) {
            return new ArrayList<>();
        }
        return images.stream()
            .map(image -> ProductImage.builder()
                .id(image.getId())
                .url(toStringValue(image.getUrl()))
                .altText(image.getAltText())
                .isPrimary(image.getIsPrimary())
                .sortOrder(image.getSortOrder())
                .build())
            .toList();
    }

    private List<th.co.carbonedge.api.dto.product.ProductImage> toDtoImages(List<ProductImage> images) {
        if (images == null) {
            return new ArrayList<>();
        }
        return images.stream()
            .map(image -> new th.co.carbonedge.api.dto.product.ProductImage()
                .id(image.getId())
                .url(toUri(image.getUrl()))
                .altText(image.getAltText())
                .isPrimary(image.getIsPrimary())
                .sortOrder(image.getSortOrder()))
            .toList();
    }

    private List<String> copyTags(List<String> tags) {
        return tags == null ? new ArrayList<>() : new ArrayList<>(tags);
    }


    private BigDecimal toBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    private String toStringValue(URI value) {
        return value == null ? null : value.toString();
    }

    private URI toUri(String value) {
        return value == null || value.isBlank() ? null : URI.create(value);
    }
}
