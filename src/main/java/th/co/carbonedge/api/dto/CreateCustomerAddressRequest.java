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
 * CreateCustomerAddressRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-16T22:01:36.911071+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class CreateCustomerAddressRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String label;

  private String recipientName;

  private String recipientPhone;

  private String line1;

  private String line2;

  private String subDistrict;

  private String district;

  private String province;

  private String postalCode;

  private String countryCode;

  private Boolean isDefault = false;

  public CreateCustomerAddressRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateCustomerAddressRequest(String recipientName, String recipientPhone, String line1, String district, String province, String postalCode, String countryCode) {
    this.recipientName = recipientName;
    this.recipientPhone = recipientPhone;
    this.line1 = line1;
    this.district = district;
    this.province = province;
    this.postalCode = postalCode;
    this.countryCode = countryCode;
  }

  public CreateCustomerAddressRequest label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
   */
  @Size(max = 100) 
  @Schema(name = "label", example = "Home", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("label")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public CreateCustomerAddressRequest recipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  /**
   * Get recipientName
   * @return recipientName
   */
  @NotNull @Size(max = 255) 
  @Schema(name = "recipientName", example = "Anchisa Prechadech", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("recipientName")
  public String getRecipientName() {
    return recipientName;
  }

  public void setRecipientName(String recipientName) {
    this.recipientName = recipientName;
  }

  public CreateCustomerAddressRequest recipientPhone(String recipientPhone) {
    this.recipientPhone = recipientPhone;
    return this;
  }

  /**
   * Get recipientPhone
   * @return recipientPhone
   */
  @NotNull 
  @Schema(name = "recipientPhone", example = "0812345678", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("recipientPhone")
  public String getRecipientPhone() {
    return recipientPhone;
  }

  public void setRecipientPhone(String recipientPhone) {
    this.recipientPhone = recipientPhone;
  }

  public CreateCustomerAddressRequest line1(String line1) {
    this.line1 = line1;
    return this;
  }

  /**
   * Get line1
   * @return line1
   */
  @NotNull @Size(max = 255) 
  @Schema(name = "line1", example = "99 ถนนสุขุมวิท", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("line1")
  public String getLine1() {
    return line1;
  }

  public void setLine1(String line1) {
    this.line1 = line1;
  }

  public CreateCustomerAddressRequest line2(String line2) {
    this.line2 = line2;
    return this;
  }

  /**
   * Get line2
   * @return line2
   */
  @Size(max = 255) 
  @Schema(name = "line2", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("line2")
  public String getLine2() {
    return line2;
  }

  public void setLine2(String line2) {
    this.line2 = line2;
  }

  public CreateCustomerAddressRequest subDistrict(String subDistrict) {
    this.subDistrict = subDistrict;
    return this;
  }

  /**
   * Get subDistrict
   * @return subDistrict
   */
  @Size(max = 150) 
  @Schema(name = "subDistrict", example = "คลองตันเหนือ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("subDistrict")
  public String getSubDistrict() {
    return subDistrict;
  }

  public void setSubDistrict(String subDistrict) {
    this.subDistrict = subDistrict;
  }

  public CreateCustomerAddressRequest district(String district) {
    this.district = district;
    return this;
  }

  /**
   * Get district
   * @return district
   */
  @NotNull @Size(max = 150) 
  @Schema(name = "district", example = "วัฒนา", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("district")
  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public CreateCustomerAddressRequest province(String province) {
    this.province = province;
    return this;
  }

  /**
   * Get province
   * @return province
   */
  @NotNull @Size(max = 150) 
  @Schema(name = "province", example = "กรุงเทพมหานคร", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("province")
  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public CreateCustomerAddressRequest postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  /**
   * Get postalCode
   * @return postalCode
   */
  @NotNull @Size(max = 20) 
  @Schema(name = "postalCode", example = "10110", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("postalCode")
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public CreateCustomerAddressRequest countryCode(String countryCode) {
    this.countryCode = countryCode;
    return this;
  }

  /**
   * Get countryCode
   * @return countryCode
   */
  @NotNull @Size(min = 2, max = 2) 
  @Schema(name = "countryCode", example = "TH", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("countryCode")
  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public CreateCustomerAddressRequest isDefault(Boolean isDefault) {
    this.isDefault = isDefault;
    return this;
  }

  /**
   * Get isDefault
   * @return isDefault
   */
  
  @Schema(name = "isDefault", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isDefault")
  public Boolean getIsDefault() {
    return isDefault;
  }

  public void setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateCustomerAddressRequest createCustomerAddressRequest = (CreateCustomerAddressRequest) o;
    return Objects.equals(this.label, createCustomerAddressRequest.label) &&
        Objects.equals(this.recipientName, createCustomerAddressRequest.recipientName) &&
        Objects.equals(this.recipientPhone, createCustomerAddressRequest.recipientPhone) &&
        Objects.equals(this.line1, createCustomerAddressRequest.line1) &&
        Objects.equals(this.line2, createCustomerAddressRequest.line2) &&
        Objects.equals(this.subDistrict, createCustomerAddressRequest.subDistrict) &&
        Objects.equals(this.district, createCustomerAddressRequest.district) &&
        Objects.equals(this.province, createCustomerAddressRequest.province) &&
        Objects.equals(this.postalCode, createCustomerAddressRequest.postalCode) &&
        Objects.equals(this.countryCode, createCustomerAddressRequest.countryCode) &&
        Objects.equals(this.isDefault, createCustomerAddressRequest.isDefault);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, recipientName, recipientPhone, line1, line2, subDistrict, district, province, postalCode, countryCode, isDefault);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateCustomerAddressRequest {\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    recipientName: ").append(toIndentedString(recipientName)).append("\n");
    sb.append("    recipientPhone: ").append(toIndentedString(recipientPhone)).append("\n");
    sb.append("    line1: ").append(toIndentedString(line1)).append("\n");
    sb.append("    line2: ").append(toIndentedString(line2)).append("\n");
    sb.append("    subDistrict: ").append(toIndentedString(subDistrict)).append("\n");
    sb.append("    district: ").append(toIndentedString(district)).append("\n");
    sb.append("    province: ").append(toIndentedString(province)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    countryCode: ").append(toIndentedString(countryCode)).append("\n");
    sb.append("    isDefault: ").append(toIndentedString(isDefault)).append("\n");
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

