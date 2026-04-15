package th.co.carbonedge.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import th.co.carbonedge.api.dto.MerchantAddressType;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreateMerchantAddressRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-12T16:05:19.119953+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class CreateMerchantAddressRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private MerchantAddressType addressType;

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

  public CreateMerchantAddressRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateMerchantAddressRequest(MerchantAddressType addressType, String line1, String district, String province, String postalCode, String countryCode) {
    this.addressType = addressType;
    this.line1 = line1;
    this.district = district;
    this.province = province;
    this.postalCode = postalCode;
    this.countryCode = countryCode;
  }

  public CreateMerchantAddressRequest addressType(MerchantAddressType addressType) {
    this.addressType = addressType;
    return this;
  }

  /**
   * Get addressType
   * @return addressType
   */
  @NotNull @Valid 
  @Schema(name = "addressType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("addressType")
  public MerchantAddressType getAddressType() {
    return addressType;
  }

  public void setAddressType(MerchantAddressType addressType) {
    this.addressType = addressType;
  }

  public CreateMerchantAddressRequest recipientName(String recipientName) {
    this.recipientName = recipientName;
    return this;
  }

  /**
   * Get recipientName
   * @return recipientName
   */
  @Size(max = 255) 
  @Schema(name = "recipientName", example = "Paw de Fume Official", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recipientName")
  public String getRecipientName() {
    return recipientName;
  }

  public void setRecipientName(String recipientName) {
    this.recipientName = recipientName;
  }

  public CreateMerchantAddressRequest recipientPhone(String recipientPhone) {
    this.recipientPhone = recipientPhone;
    return this;
  }

  /**
   * Get recipientPhone
   * @return recipientPhone
   */
  
  @Schema(name = "recipientPhone", example = "0899999999", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("recipientPhone")
  public String getRecipientPhone() {
    return recipientPhone;
  }

  public void setRecipientPhone(String recipientPhone) {
    this.recipientPhone = recipientPhone;
  }

  public CreateMerchantAddressRequest line1(String line1) {
    this.line1 = line1;
    return this;
  }

  /**
   * Get line1
   * @return line1
   */
  @NotNull @Size(max = 255) 
  @Schema(name = "line1", example = "88 ถนนสุขุมวิท", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("line1")
  public String getLine1() {
    return line1;
  }

  public void setLine1(String line1) {
    this.line1 = line1;
  }

  public CreateMerchantAddressRequest line2(String line2) {
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

  public CreateMerchantAddressRequest subDistrict(String subDistrict) {
    this.subDistrict = subDistrict;
    return this;
  }

  /**
   * Get subDistrict
   * @return subDistrict
   */
  @Size(max = 150) 
  @Schema(name = "subDistrict", example = "พระโขนง", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("subDistrict")
  public String getSubDistrict() {
    return subDistrict;
  }

  public void setSubDistrict(String subDistrict) {
    this.subDistrict = subDistrict;
  }

  public CreateMerchantAddressRequest district(String district) {
    this.district = district;
    return this;
  }

  /**
   * Get district
   * @return district
   */
  @NotNull @Size(max = 150) 
  @Schema(name = "district", example = "คลองเตย", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("district")
  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public CreateMerchantAddressRequest province(String province) {
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

  public CreateMerchantAddressRequest postalCode(String postalCode) {
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

  public CreateMerchantAddressRequest countryCode(String countryCode) {
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

  public CreateMerchantAddressRequest isDefault(Boolean isDefault) {
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
    CreateMerchantAddressRequest createMerchantAddressRequest = (CreateMerchantAddressRequest) o;
    return Objects.equals(this.addressType, createMerchantAddressRequest.addressType) &&
        Objects.equals(this.recipientName, createMerchantAddressRequest.recipientName) &&
        Objects.equals(this.recipientPhone, createMerchantAddressRequest.recipientPhone) &&
        Objects.equals(this.line1, createMerchantAddressRequest.line1) &&
        Objects.equals(this.line2, createMerchantAddressRequest.line2) &&
        Objects.equals(this.subDistrict, createMerchantAddressRequest.subDistrict) &&
        Objects.equals(this.district, createMerchantAddressRequest.district) &&
        Objects.equals(this.province, createMerchantAddressRequest.province) &&
        Objects.equals(this.postalCode, createMerchantAddressRequest.postalCode) &&
        Objects.equals(this.countryCode, createMerchantAddressRequest.countryCode) &&
        Objects.equals(this.isDefault, createMerchantAddressRequest.isDefault);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressType, recipientName, recipientPhone, line1, line2, subDistrict, district, province, postalCode, countryCode, isDefault);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateMerchantAddressRequest {\n");
    sb.append("    addressType: ").append(toIndentedString(addressType)).append("\n");
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

