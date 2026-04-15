package th.co.carbonedge.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AddCartItemRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-12T16:05:19.119953+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class AddCartItemRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long productId;

  private Integer quantity;

  public AddCartItemRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AddCartItemRequest(Long productId, Integer quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public AddCartItemRequest productId(Long productId) {
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

  public AddCartItemRequest quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Get quantity
   * minimum: 1
   * @return quantity
   */
  @NotNull @Min(1) 
  @Schema(name = "quantity", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddCartItemRequest addCartItemRequest = (AddCartItemRequest) o;
    return Objects.equals(this.productId, addCartItemRequest.productId) &&
        Objects.equals(this.quantity, addCartItemRequest.quantity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, quantity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddCartItemRequest {\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
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

