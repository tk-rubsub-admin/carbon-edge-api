package th.co.carbonedge.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import th.co.carbonedge.api.dto.Product;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ProductPageResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-12T16:05:19.119953+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class ProductPageResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @Valid
  private List<@Valid Product> data = new ArrayList<>();

  private Integer page;

  private Integer size;

  private Long totalElements;

  private Integer totalPages;

  private Boolean hasNext;

  public ProductPageResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ProductPageResponse(List<@Valid Product> data, Integer page, Integer size, Long totalElements, Integer totalPages, Boolean hasNext) {
    this.data = data;
    this.page = page;
    this.size = size;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.hasNext = hasNext;
  }

  public ProductPageResponse data(List<@Valid Product> data) {
    this.data = data;
    return this;
  }

  public ProductPageResponse addDataItem(Product dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

  /**
   * Get data
   * @return data
   */
  @NotNull @Valid 
  @Schema(name = "data", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("data")
  public List<@Valid Product> getData() {
    return data;
  }

  public void setData(List<@Valid Product> data) {
    this.data = data;
  }

  public ProductPageResponse page(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Get page
   * @return page
   */
  @NotNull 
  @Schema(name = "page", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("page")
  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public ProductPageResponse size(Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * @return size
   */
  @NotNull 
  @Schema(name = "size", example = "20", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("size")
  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public ProductPageResponse totalElements(Long totalElements) {
    this.totalElements = totalElements;
    return this;
  }

  /**
   * Get totalElements
   * @return totalElements
   */
  @NotNull 
  @Schema(name = "totalElements", example = "125", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalElements")
  public Long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }

  public ProductPageResponse totalPages(Integer totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Get totalPages
   * @return totalPages
   */
  @NotNull 
  @Schema(name = "totalPages", example = "7", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalPages")
  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public ProductPageResponse hasNext(Boolean hasNext) {
    this.hasNext = hasNext;
    return this;
  }

  /**
   * Get hasNext
   * @return hasNext
   */
  @NotNull 
  @Schema(name = "hasNext", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("hasNext")
  public Boolean getHasNext() {
    return hasNext;
  }

  public void setHasNext(Boolean hasNext) {
    this.hasNext = hasNext;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductPageResponse productPageResponse = (ProductPageResponse) o;
    return Objects.equals(this.data, productPageResponse.data) &&
        Objects.equals(this.page, productPageResponse.page) &&
        Objects.equals(this.size, productPageResponse.size) &&
        Objects.equals(this.totalElements, productPageResponse.totalElements) &&
        Objects.equals(this.totalPages, productPageResponse.totalPages) &&
        Objects.equals(this.hasNext, productPageResponse.hasNext);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data, page, size, totalElements, totalPages, hasNext);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProductPageResponse {\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
    sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
    sb.append("    hasNext: ").append(toIndentedString(hasNext)).append("\n");
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

