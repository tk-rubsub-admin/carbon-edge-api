package th.co.carbonedge.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.net.URI;
import th.co.carbonedge.api.dto.MerchantBusinessType;
import th.co.carbonedge.api.dto.MerchantStatus;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreateMerchantRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-12T16:05:19.119953+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class CreateMerchantRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String merchantCode;

  private String shopName;

  private String shopSlug;

  private String legalName;

  private String taxId;

  private String email;

  private String phoneNumber;

  private String supportEmail;

  private String supportPhone;

  private String description;

  private URI logoUrl;

  private URI coverImageUrl;

  private MerchantBusinessType businessType;

  private String registeredCountry;

  private String currency;

  private Double commissionRate;

  private Boolean isOfficial = false;

  private MerchantStatus status;

  public CreateMerchantRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateMerchantRequest(String merchantCode, String shopName, String shopSlug, MerchantStatus status) {
    this.merchantCode = merchantCode;
    this.shopName = shopName;
    this.shopSlug = shopSlug;
    this.status = status;
  }

  public CreateMerchantRequest merchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
    return this;
  }

  /**
   * Get merchantCode
   * @return merchantCode
   */
  @NotNull @Size(max = 100) 
  @Schema(name = "merchantCode", example = "MRC-0001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("merchantCode")
  public String getMerchantCode() {
    return merchantCode;
  }

  public void setMerchantCode(String merchantCode) {
    this.merchantCode = merchantCode;
  }

  public CreateMerchantRequest shopName(String shopName) {
    this.shopName = shopName;
    return this;
  }

  /**
   * Get shopName
   * @return shopName
   */
  @NotNull @Size(max = 255) 
  @Schema(name = "shopName", example = "Paw de Fume Official", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("shopName")
  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public CreateMerchantRequest shopSlug(String shopSlug) {
    this.shopSlug = shopSlug;
    return this;
  }

  /**
   * Get shopSlug
   * @return shopSlug
   */
  @NotNull @Size(max = 255) 
  @Schema(name = "shopSlug", example = "paw-de-fume-official", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("shopSlug")
  public String getShopSlug() {
    return shopSlug;
  }

  public void setShopSlug(String shopSlug) {
    this.shopSlug = shopSlug;
  }

  public CreateMerchantRequest legalName(String legalName) {
    this.legalName = legalName;
    return this;
  }

  /**
   * Get legalName
   * @return legalName
   */
  @Size(max = 255) 
  @Schema(name = "legalName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("legalName")
  public String getLegalName() {
    return legalName;
  }

  public void setLegalName(String legalName) {
    this.legalName = legalName;
  }

  public CreateMerchantRequest taxId(String taxId) {
    this.taxId = taxId;
    return this;
  }

  /**
   * Get taxId
   * @return taxId
   */
  @Size(max = 50) 
  @Schema(name = "taxId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("taxId")
  public String getTaxId() {
    return taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  public CreateMerchantRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public CreateMerchantRequest phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   * @return phoneNumber
   */
  
  @Schema(name = "phoneNumber", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public CreateMerchantRequest supportEmail(String supportEmail) {
    this.supportEmail = supportEmail;
    return this;
  }

  /**
   * Get supportEmail
   * @return supportEmail
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "supportEmail", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("supportEmail")
  public String getSupportEmail() {
    return supportEmail;
  }

  public void setSupportEmail(String supportEmail) {
    this.supportEmail = supportEmail;
  }

  public CreateMerchantRequest supportPhone(String supportPhone) {
    this.supportPhone = supportPhone;
    return this;
  }

  /**
   * Get supportPhone
   * @return supportPhone
   */
  
  @Schema(name = "supportPhone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("supportPhone")
  public String getSupportPhone() {
    return supportPhone;
  }

  public void setSupportPhone(String supportPhone) {
    this.supportPhone = supportPhone;
  }

  public CreateMerchantRequest description(String description) {
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

  public CreateMerchantRequest logoUrl(URI logoUrl) {
    this.logoUrl = logoUrl;
    return this;
  }

  /**
   * Get logoUrl
   * @return logoUrl
   */
  @Valid 
  @Schema(name = "logoUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("logoUrl")
  public URI getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(URI logoUrl) {
    this.logoUrl = logoUrl;
  }

  public CreateMerchantRequest coverImageUrl(URI coverImageUrl) {
    this.coverImageUrl = coverImageUrl;
    return this;
  }

  /**
   * Get coverImageUrl
   * @return coverImageUrl
   */
  @Valid 
  @Schema(name = "coverImageUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("coverImageUrl")
  public URI getCoverImageUrl() {
    return coverImageUrl;
  }

  public void setCoverImageUrl(URI coverImageUrl) {
    this.coverImageUrl = coverImageUrl;
  }

  public CreateMerchantRequest businessType(MerchantBusinessType businessType) {
    this.businessType = businessType;
    return this;
  }

  /**
   * Get businessType
   * @return businessType
   */
  @Valid 
  @Schema(name = "businessType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("businessType")
  public MerchantBusinessType getBusinessType() {
    return businessType;
  }

  public void setBusinessType(MerchantBusinessType businessType) {
    this.businessType = businessType;
  }

  public CreateMerchantRequest registeredCountry(String registeredCountry) {
    this.registeredCountry = registeredCountry;
    return this;
  }

  /**
   * Get registeredCountry
   * @return registeredCountry
   */
  @Size(min = 2, max = 2) 
  @Schema(name = "registeredCountry", example = "TH", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("registeredCountry")
  public String getRegisteredCountry() {
    return registeredCountry;
  }

  public void setRegisteredCountry(String registeredCountry) {
    this.registeredCountry = registeredCountry;
  }

  public CreateMerchantRequest currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
   */
  @Size(min = 3, max = 3) 
  @Schema(name = "currency", example = "THB", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public CreateMerchantRequest commissionRate(Double commissionRate) {
    this.commissionRate = commissionRate;
    return this;
  }

  /**
   * Get commissionRate
   * minimum: 0
   * @return commissionRate
   */
  @DecimalMin("0") 
  @Schema(name = "commissionRate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commissionRate")
  public Double getCommissionRate() {
    return commissionRate;
  }

  public void setCommissionRate(Double commissionRate) {
    this.commissionRate = commissionRate;
  }

  public CreateMerchantRequest isOfficial(Boolean isOfficial) {
    this.isOfficial = isOfficial;
    return this;
  }

  /**
   * Get isOfficial
   * @return isOfficial
   */
  
  @Schema(name = "isOfficial", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isOfficial")
  public Boolean getIsOfficial() {
    return isOfficial;
  }

  public void setIsOfficial(Boolean isOfficial) {
    this.isOfficial = isOfficial;
  }

  public CreateMerchantRequest status(MerchantStatus status) {
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
  public MerchantStatus getStatus() {
    return status;
  }

  public void setStatus(MerchantStatus status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateMerchantRequest createMerchantRequest = (CreateMerchantRequest) o;
    return Objects.equals(this.merchantCode, createMerchantRequest.merchantCode) &&
        Objects.equals(this.shopName, createMerchantRequest.shopName) &&
        Objects.equals(this.shopSlug, createMerchantRequest.shopSlug) &&
        Objects.equals(this.legalName, createMerchantRequest.legalName) &&
        Objects.equals(this.taxId, createMerchantRequest.taxId) &&
        Objects.equals(this.email, createMerchantRequest.email) &&
        Objects.equals(this.phoneNumber, createMerchantRequest.phoneNumber) &&
        Objects.equals(this.supportEmail, createMerchantRequest.supportEmail) &&
        Objects.equals(this.supportPhone, createMerchantRequest.supportPhone) &&
        Objects.equals(this.description, createMerchantRequest.description) &&
        Objects.equals(this.logoUrl, createMerchantRequest.logoUrl) &&
        Objects.equals(this.coverImageUrl, createMerchantRequest.coverImageUrl) &&
        Objects.equals(this.businessType, createMerchantRequest.businessType) &&
        Objects.equals(this.registeredCountry, createMerchantRequest.registeredCountry) &&
        Objects.equals(this.currency, createMerchantRequest.currency) &&
        Objects.equals(this.commissionRate, createMerchantRequest.commissionRate) &&
        Objects.equals(this.isOfficial, createMerchantRequest.isOfficial) &&
        Objects.equals(this.status, createMerchantRequest.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(merchantCode, shopName, shopSlug, legalName, taxId, email, phoneNumber, supportEmail, supportPhone, description, logoUrl, coverImageUrl, businessType, registeredCountry, currency, commissionRate, isOfficial, status);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateMerchantRequest {\n");
    sb.append("    merchantCode: ").append(toIndentedString(merchantCode)).append("\n");
    sb.append("    shopName: ").append(toIndentedString(shopName)).append("\n");
    sb.append("    shopSlug: ").append(toIndentedString(shopSlug)).append("\n");
    sb.append("    legalName: ").append(toIndentedString(legalName)).append("\n");
    sb.append("    taxId: ").append(toIndentedString(taxId)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    supportEmail: ").append(toIndentedString(supportEmail)).append("\n");
    sb.append("    supportPhone: ").append(toIndentedString(supportPhone)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    logoUrl: ").append(toIndentedString(logoUrl)).append("\n");
    sb.append("    coverImageUrl: ").append(toIndentedString(coverImageUrl)).append("\n");
    sb.append("    businessType: ").append(toIndentedString(businessType)).append("\n");
    sb.append("    registeredCountry: ").append(toIndentedString(registeredCountry)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    commissionRate: ").append(toIndentedString(commissionRate)).append("\n");
    sb.append("    isOfficial: ").append(toIndentedString(isOfficial)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
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

