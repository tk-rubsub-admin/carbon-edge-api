package th.co.carbonedge.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.net.URI;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProductImage
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-16T22:01:36.911071+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class ProductImage implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private URI url;

  private String altText;

  private Boolean isPrimary;

  private Integer sortOrder;

  public ProductImage() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductImage(URI url, Integer sortOrder) {
    this.url = url;
    this.sortOrder = sortOrder;
  }

  public ProductImage id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  
  @Schema(name = "id", example = "101", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ProductImage url(URI url) {
    this.url = url;
    return this;
  }

  /**
   * Get url
   * @return url
   */
  @NotNull @Valid 
  @Schema(name = "url", example = "https://cdn.example.com/products/p001/main.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("url")
  public URI getUrl() {
    return url;
  }

  public void setUrl(URI url) {
    this.url = url;
  }

  public ProductImage altText(String altText) {
    this.altText = altText;
    return this;
  }

  /**
   * Get altText
   * @return altText
   */
  
  @Schema(name = "altText", example = "Front view of product", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("altText")
  public String getAltText() {
    return altText;
  }

  public void setAltText(String altText) {
    this.altText = altText;
  }

  public ProductImage isPrimary(Boolean isPrimary) {
    this.isPrimary = isPrimary;
    return this;
  }

  /**
   * Get isPrimary
   * @return isPrimary
   */
  
  @Schema(name = "isPrimary", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isPrimary")
  public Boolean getIsPrimary() {
    return isPrimary;
  }

  public void setIsPrimary(Boolean isPrimary) {
    this.isPrimary = isPrimary;
  }

  public ProductImage sortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
    return this;
  }

  /**
   * Get sortOrder
   * minimum: 0
   * @return sortOrder
   */
  @NotNull @Min(0) 
  @Schema(name = "sortOrder", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sortOrder")
  public Integer getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(Integer sortOrder) {
    this.sortOrder = sortOrder;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductImage productImage = (ProductImage) o;
    return Objects.equals(this.id, productImage.id) &&
        Objects.equals(this.url, productImage.url) &&
        Objects.equals(this.altText, productImage.altText) &&
        Objects.equals(this.isPrimary, productImage.isPrimary) &&
        Objects.equals(this.sortOrder, productImage.sortOrder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, url, altText, isPrimary, sortOrder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductImage {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    altText: ").append(toIndentedString(altText)).append("\n");
    sb.append("    isPrimary: ").append(toIndentedString(isPrimary)).append("\n");
    sb.append("    sortOrder: ").append(toIndentedString(sortOrder)).append("\n");
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

