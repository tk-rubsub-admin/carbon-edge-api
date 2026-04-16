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
 * UpdateCustomerAddressRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-16T22:01:36.911071+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class UpdateCustomerAddressRequest implements Serializable {

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

  private Boolean isDefault;

  public UpdateCustomerAddressRequest label(String label) {
    this.label = label;
    return this;
  }

  /**
   * Get label
   * @return label
   */
  @Size(max = 100) 
  @Schema(name = "label", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("label")
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public UpdateCustomerAddressRequest recipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  /**
   * Get recipientName
   * @return recipientName
   */
  @Size(max = 255) 
  @Schema(name = "recipientName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recipientName")
  public String getRecipientName() {
    return recipientName;
  }

  public void setRecipientName(String recipientName) {
    this.recipientName = recipientName;
  }

  public UpdateCustomerAddressRequest recipientPhone(String recipientPhone) {
    this.recipientPhone = recipientPhone;
    return this;
  }

  /**
   * Get recipientPhone
   * @return recipientPhone
   */
  
  @Schema(name = "recipientPhone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recipientPhone")
  public String getRecipientPhone() {
    return recipientPhone;
  }

  public void setRecipientPhone(String recipientPhone) {
    this.recipientPhone = recipientPhone;
  }

  public UpdateCustomerAddressRequest line1(String line1) {
    this.line1 = line1;
    return this;
  }

  /**
   * Get line1
   * @return line1
   */
  @Size(max = 255) 
  @Schema(name = "line1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("line1")
  public String getLine1() {
    return line1;
  }

  public void setLine1(String line1) {
    this.line1 = line1;
  }

  public UpdateCustomerAddressRequest line2(String line2) {
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

  public UpdateCustomerAddressRequest subDistrict(String subDistrict) {
    this.subDistrict = subDistrict;
    return this;
  }

  /**
   * Get subDistrict
   * @return subDistrict
   */
  @Size(max = 150) 
  @Schema(name = "subDistrict", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("subDistrict")
  public String getSubDistrict() {
    return subDistrict;
  }

  public void setSubDistrict(String subDistrict) {
    this.subDistrict = subDistrict;
  }

  public UpdateCustomerAddressRequest district(String district) {
    this.district = district;
    return this;
  }

  /**
   * Get district
   * @return district
   */
  @Size(max = 150) 
  @Schema(name = "district", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("district")
  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public UpdateCustomerAddressRequest province(String province) {
    this.province = province;
    return this;
  }

  /**
   * Get province
   * @return province
   */
  @Size(max = 150) 
  @Schema(name = "province", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("province")
  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public UpdateCustomerAddressRequest postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  /**
   * Get postalCode
   * @return postalCode
   */
  @Size(max = 20) 
  @Schema(name = "postalCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("postalCode")
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public UpdateCustomerAddressRequest countryCode(String countryCode) {
    this.countryCode = countryCode;
    return this;
  }

  /**
   * Get countryCode
   * @return countryCode
   */
  @Size(min = 2, max = 2) 
  @Schema(name = "countryCode", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("countryCode")
  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public UpdateCustomerAddressRequest isDefault(Boolean isDefault) {
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
    UpdateCustomerAddressRequest updateCustomerAddressRequest = (UpdateCustomerAddressRequest) o;
    return Objects.equals(this.label, updateCustomerAddressRequest.label) &&
        Objects.equals(this.recipientName, updateCustomerAddressRequest.recipientName) &&
        Objects.equals(this.recipientPhone, updateCustomerAddressRequest.recipientPhone) &&
        Objects.equals(this.line1, updateCustomerAddressRequest.line1) &&
        Objects.equals(this.line2, updateCustomerAddressRequest.line2) &&
        Objects.equals(this.subDistrict, updateCustomerAddressRequest.subDistrict) &&
        Objects.equals(this.district, updateCustomerAddressRequest.district) &&
        Objects.equals(this.province, updateCustomerAddressRequest.province) &&
        Objects.equals(this.postalCode, updateCustomerAddressRequest.postalCode) &&
        Objects.equals(this.countryCode, updateCustomerAddressRequest.countryCode) &&
        Objects.equals(this.isDefault, updateCustomerAddressRequest.isDefault);
  }

  @Override
  public int hashCode() {
    return Objects.hash(label, recipientName, recipientPhone, line1, line2, subDistrict, district, province, postalCode, countryCode, isDefault);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateCustomerAddressRequest {\n");
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

