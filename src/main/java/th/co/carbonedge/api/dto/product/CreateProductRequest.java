package th.co.carbonedge.api.dto.product;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import th.co.carbonedge.api.constant.ProductStatus;

import java.io.Serializable;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


import jakarta.annotation.Generated;

/**
 * CreateProductRequest
 */

@Data
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-02T21:16:13.425950+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class CreateProductRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String sku;

  private String name;

  private String slug;

  private String shortDescription;

  private String description;

  private String brand;

  private Long categoryId;

  private String merchantId;

  private Double price;

  private String currency;

  private Integer stockQuantity;

  private ProductStatus status;

  private Boolean isFeatured = false;

  private URI thumbnailUrl;

  @Valid
  private List<@Valid ProductImage> images = new ArrayList<>();

  @Valid
  private List<String> tags = new ArrayList<>();

  public CreateProductRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateProductRequest(String sku, String name, String slug, Double price, String currency, Integer stockQuantity, ProductStatus status) {
    this.sku = sku;
    this.name = name;
    this.slug = slug;
    this.price = price;
    this.currency = currency;
    this.stockQuantity = stockQuantity;
    this.status = status;
  }

  public CreateProductRequest sku(String sku) {
    this.sku = sku;
    return this;
  }

  /**
   * Get sku
   * @return sku
   */
  @NotNull @Size(max = 100) 
  @Schema(name = "sku", example = "SKU-TSHIRT-001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sku")
  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public CreateProductRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull @Size(max = 255) 
  @Schema(name = "name", example = "Premium Oversized T-Shirt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateProductRequest slug(String slug) {
    this.slug = slug;
    return this;
  }

  /**
   * Get slug
   * @return slug
   */
  @NotNull @Size(max = 255) 
  @Schema(name = "slug", example = "premium-oversized-t-shirt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("slug")
  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public CreateProductRequest shortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
    return this;
  }

  /**
   * Get shortDescription
   * @return shortDescription
   */
  @Size(max = 500) 
  @Schema(name = "shortDescription", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("shortDescription")
  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public CreateProductRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CreateProductRequest brand(String brand) {
    this.brand = brand;
    return this;
  }

  /**
   * Get brand
   * @return brand
   */
  @Size(max = 100) 
  @Schema(name = "brand", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("brand")
  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public CreateProductRequest categoryId(Long categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  /**
   * Get categoryId
   * @return categoryId
   */
  
  @Schema(name = "categoryId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("categoryId")
  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public CreateProductRequest price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * minimum: 0
   * @return price
   */
  @NotNull @DecimalMin("0") 
  @Schema(name = "price", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public CreateProductRequest currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
   */
  @NotNull @Size(min = 3, max = 3) 
  @Schema(name = "currency", example = "THB", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public CreateProductRequest stockQuantity(Integer stockQuantity) {
    this.stockQuantity = stockQuantity;
    return this;
  }

  /**
   * Get stockQuantity
   * minimum: 0
   * @return stockQuantity
   */
  @NotNull @Min(0) 
  @Schema(name = "stockQuantity", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("stockQuantity")
  public Integer getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(Integer stockQuantity) {
    this.stockQuantity = stockQuantity;
  }

  public CreateProductRequest status(ProductStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @NotNull @Valid 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("status")
  public ProductStatus getStatus() {
    return status;
  }

  public void setStatus(ProductStatus status) {
    this.status = status;
  }

  public CreateProductRequest isFeatured(Boolean isFeatured) {
    this.isFeatured = isFeatured;
    return this;
  }

  /**
   * Get isFeatured
   * @return isFeatured
   */
  
  @Schema(name = "isFeatured", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isFeatured")
  public Boolean getIsFeatured() {
    return isFeatured;
  }

  public void setIsFeatured(Boolean isFeatured) {
    this.isFeatured = isFeatured;
  }

  public CreateProductRequest thumbnailUrl(URI thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
    return this;
  }

  /**
   * Get thumbnailUrl
   * @return thumbnailUrl
   */
  @Valid 
  @Schema(name = "thumbnailUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("thumbnailUrl")
  public URI getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(URI thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  public CreateProductRequest images(List<@Valid ProductImage> images) {
    this.images = images;
    return this;
  }

  public CreateProductRequest addImagesItem(ProductImage imagesItem) {
    if (this.images == null) {
      this.images = new ArrayList<>();
    }
    this.images.add(imagesItem);
    return this;
  }

  /**
   * Get images
   * @return images
   */
  @Valid 
  @Schema(name = "images", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("images")
  public List<@Valid ProductImage> getImages() {
    return images;
  }

  public void setImages(List<@Valid ProductImage> images) {
    this.images = images;
  }

  public CreateProductRequest tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public CreateProductRequest addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Get tags
   * @return tags
   */
  
  @Schema(name = "tags", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tags")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateProductRequest createProductRequest = (CreateProductRequest) o;
    return Objects.equals(this.sku, createProductRequest.sku) &&
        Objects.equals(this.name, createProductRequest.name) &&
        Objects.equals(this.slug, createProductRequest.slug) &&
        Objects.equals(this.shortDescription, createProductRequest.shortDescription) &&
        Objects.equals(this.description, createProductRequest.description) &&
        Objects.equals(this.brand, createProductRequest.brand) &&
        Objects.equals(this.categoryId, createProductRequest.categoryId) &&
        Objects.equals(this.price, createProductRequest.price) &&
        Objects.equals(this.currency, createProductRequest.currency) &&
        Objects.equals(this.stockQuantity, createProductRequest.stockQuantity) &&
        Objects.equals(this.status, createProductRequest.status) &&
        Objects.equals(this.isFeatured, createProductRequest.isFeatured) &&
        Objects.equals(this.thumbnailUrl, createProductRequest.thumbnailUrl) &&
        Objects.equals(this.images, createProductRequest.images) &&
        Objects.equals(this.tags, createProductRequest.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sku, name, slug, shortDescription, description, brand, categoryId, price, currency, stockQuantity, status, isFeatured, thumbnailUrl, images, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateProductRequest {\n");
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    slug: ").append(toIndentedString(slug)).append("\n");
    sb.append("    shortDescription: ").append(toIndentedString(shortDescription)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    categoryId: ").append(toIndentedString(categoryId)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    stockQuantity: ").append(toIndentedString(stockQuantity)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    isFeatured: ").append(toIndentedString(isFeatured)).append("\n");
    sb.append("    thumbnailUrl: ").append(toIndentedString(thumbnailUrl)).append("\n");
    sb.append("    images: ").append(toIndentedString(images)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
