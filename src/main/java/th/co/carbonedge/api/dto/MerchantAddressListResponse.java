package th.co.carbonedge.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import th.co.carbonedge.api.dto.MerchantAddress;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * MerchantAddressListResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-12T16:05:19.119953+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class MerchantAddressListResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @Valid
  private List<@Valid MerchantAddress> data = new ArrayList<>();

  public MerchantAddressListResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MerchantAddressListResponse(List<@Valid MerchantAddress> data) {
    this.data = data;
  }

  public MerchantAddressListResponse data(List<@Valid MerchantAddress> data) {
    this.data = data;
    return this;
  }

  public MerchantAddressListResponse addDataItem(MerchantAddress dataItem) {
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
  public List<@Valid MerchantAddress> getData() {
    return data;
  }

  public void setData(List<@Valid MerchantAddress> data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MerchantAddressListResponse merchantAddressListResponse = (MerchantAddressListResponse) o;
    return Objects.equals(this.data, merchantAddressListResponse.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MerchantAddressListResponse {\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

