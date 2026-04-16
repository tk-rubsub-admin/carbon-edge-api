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
 * CartItem
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-16T22:01:36.911071+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class CartItem implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private Long productId;

  private String productName;

  private String sku;

  private URI thumbnailUrl;

  private Integer quantity;

  private Double unitPrice;

  private Double lineTotal;

  public CartItem() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CartItem(Long id, Long productId, String productName, String sku, Integer quantity, Double unitPrice, Double lineTotal) {
    this.id = id;
    this.productId = productId;
    this.productName = productName;
    this.sku = sku;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.lineTotal = lineTotal;
  }

  public CartItem id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull 
  @Schema(name = "id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CartItem productId(Long productId) {
    this.productId = productId;
    return this;
  }

  /**
   * Get productId
   * @return productId
   */
  @NotNull 
  @Schema(name = "productId", example = "101", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("productId")
  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public CartItem productName(String productName) {
    this.productName = productName;
    return this;
  }

  /**
   * Get productName
   * @return productName
   */
  @NotNull 
  @Schema(name = "productName", example = "Paw de Fume Cloudy", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("productName")
  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public CartItem sku(String sku) {
    this.sku = sku;
    return this;
  }

  /**
   * Get sku
   * @return sku
   */
  @NotNull 
  @Schema(name = "sku", example = "PDF-CLOUDY-30ML", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("sku")
  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public CartItem thumbnailUrl(URI thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
    return this;
  }

  /**
   * Get thumbnailUrl
   * @return thumbnailUrl
   */
  @Valid 
  @Schema(name = "thumbnailUrl", example = "https://cdn.example.com/products/cloudy-thumb.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("thumbnailUrl")
  public URI getThumbnailUrl() {
    return thumbnailUrl;
  }

  public void setThumbnailUrl(URI thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }

  public CartItem quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * minimum: 1
   * @return quantity
   */
  @NotNull @Min(1) 
  @Schema(name = "quantity", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public CartItem unitPrice(Double unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

  /**
   * Get unitPrice
   * minimum: 0
   * @return unitPrice
   */
  @NotNull @DecimalMin("0") 
  @Schema(name = "unitPrice", example = "290.0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("unitPrice")
  public Double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public CartItem lineTotal(Double lineTotal) {
    this.lineTotal = lineTotal;
    return this;
  }

  /**
   * Get lineTotal
   * minimum: 0
   * @return lineTotal
   */
  @NotNull @DecimalMin("0") 
  @Schema(name = "lineTotal", example = "580.0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("lineTotal")
  public Double getLineTotal() {
    return lineTotal;
  }

  public void setLineTotal(Double lineTotal) {
    this.lineTotal = lineTotal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CartItem cartItem = (CartItem) o;
    return Objects.equals(this.id, cartItem.id) &&
        Objects.equals(this.productId, cartItem.productId) &&
        Objects.equals(this.productName, cartItem.productName) &&
        Objects.equals(this.sku, cartItem.sku) &&
        Objects.equals(this.thumbnailUrl, cartItem.thumbnailUrl) &&
        Objects.equals(this.quantity, cartItem.quantity) &&
        Objects.equals(this.unitPrice, cartItem.unitPrice) &&
        Objects.equals(this.lineTotal, cartItem.lineTotal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, productId, productName, sku, thumbnailUrl, quantity, unitPrice, lineTotal);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CartItem {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
    sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
    sb.append("    thumbnailUrl: ").append(toIndentedString(thumbnailUrl)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    unitPrice: ").append(toIndentedString(unitPrice)).append("\n");
    sb.append("    lineTotal: ").append(toIndentedString(lineTotal)).append("\n");
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

