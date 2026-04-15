package th.co.carbonedge.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.net.URI;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
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
 * Merchant
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-12T16:05:19.119953+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class Merchant implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

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

  private Double ratingAverage;

  private Boolean isOfficial;

  private MerchantStatus status;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime verifiedAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  public Merchant() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Merchant(Long id, String merchantCode, String shopName, String shopSlug, MerchantStatus status, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
    this.id = id;
    this.merchantCode = merchantCode;
    this.shopName = shopName;
    this.shopSlug = shopSlug;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Merchant id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @NotNull 
  @Schema(name = "id", example = "2001", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Merchant merchantCode(String merchantCode) {
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

  public Merchant shopName(String shopName) {
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

  public Merchant shopSlug(String shopSlug) {
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

  public Merchant legalName(String legalName) {
    this.legalName = legalName;
    return this;
  }

  /**
   * Get legalName
   * @return legalName
   */
  @Size(max = 255) 
  @Schema(name = "legalName", example = "Paw de Fume Co., Ltd.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("legalName")
  public String getLegalName() {
    return legalName;
  }

  public void setLegalName(String legalName) {
    this.legalName = legalName;
  }

  public Merchant taxId(String taxId) {
    this.taxId = taxId;
    return this;
  }

  /**
   * Get taxId
   * @return taxId
   */
  @Size(max = 50) 
  @Schema(name = "taxId", example = "0105559999999", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("taxId")
  public String getTaxId() {
    return taxId;
  }

  public void setTaxId(String taxId) {
    this.taxId = taxId;
  }

  public Merchant email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "email", example = "hello@pawdefume.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Merchant phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   * @return phoneNumber
   */
  
  @Schema(name = "phoneNumber", example = "0899999999", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public Merchant supportEmail(String supportEmail) {
    this.supportEmail = supportEmail;
    return this;
  }

  /**
   * Get supportEmail
   * @return supportEmail
   */
  @jakarta.validation.constraints.Email 
  @Schema(name = "supportEmail", example = "support@pawdefume.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("supportEmail")
  public String getSupportEmail() {
    return supportEmail;
  }

  public void setSupportEmail(String supportEmail) {
    this.supportEmail = supportEmail;
  }

  public Merchant supportPhone(String supportPhone) {
    this.supportPhone = supportPhone;
    return this;
  }

  /**
   * Get supportPhone
   * @return supportPhone
   */
  
  @Schema(name = "supportPhone", example = "0899999999", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("supportPhone")
  public String getSupportPhone() {
    return supportPhone;
  }

  public void setSupportPhone(String supportPhone) {
    this.supportPhone = supportPhone;
  }

  public Merchant description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  
  @Schema(name = "description", example = "Premium perfume for dogs and cats", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Merchant logoUrl(URI logoUrl) {
    this.logoUrl = logoUrl;
    return this;
  }

  /**
   * Get logoUrl
   * @return logoUrl
   */
  @Valid 
  @Schema(name = "logoUrl", example = "https://cdn.example.com/merchants/paw-de-fume/logo.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("logoUrl")
  public URI getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(URI logoUrl) {
    this.logoUrl = logoUrl;
  }

  public Merchant coverImageUrl(URI coverImageUrl) {
    this.coverImageUrl = coverImageUrl;
    return this;
  }

  /**
   * Get coverImageUrl
   * @return coverImageUrl
   */
  @Valid 
  @Schema(name = "coverImageUrl", example = "https://cdn.example.com/merchants/paw-de-fume/cover.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("coverImageUrl")
  public URI getCoverImageUrl() {
    return coverImageUrl;
  }

  public void setCoverImageUrl(URI coverImageUrl) {
    this.coverImageUrl = coverImageUrl;
  }

  public Merchant businessType(MerchantBusinessType businessType) {
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

  public Merchant registeredCountry(String registeredCountry) {
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

  public Merchant currency(String currency) {
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

  public Merchant commissionRate(Double commissionRate) {
    this.commissionRate = commissionRate;
    return this;
  }

  /**
   * Get commissionRate
   * minimum: 0
   * @return commissionRate
   */
  @DecimalMin("0") 
  @Schema(name = "commissionRate", example = "10.0", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("commissionRate")
  public Double getCommissionRate() {
    return commissionRate;
  }

  public void setCommissionRate(Double commissionRate) {
    this.commissionRate = commissionRate;
  }

  public Merchant ratingAverage(Double ratingAverage) {
    this.ratingAverage = ratingAverage;
    return this;
  }

  /**
   * Get ratingAverage
   * minimum: 0
   * @return ratingAverage
   */
  @DecimalMin("0") 
  @Schema(name = "ratingAverage", example = "4.8", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("ratingAverage")
  public Double getRatingAverage() {
    return ratingAverage;
  }

  public void setRatingAverage(Double ratingAverage) {
    this.ratingAverage = ratingAverage;
  }

  public Merchant isOfficial(Boolean isOfficial) {
    this.isOfficial = isOfficial;
    return this;
  }

  /**
   * Get isOfficial
   * @return isOfficial
   */
  
  @Schema(name = "isOfficial", example = "true", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isOfficial")
  public Boolean getIsOfficial() {
    return isOfficial;
  }

  public void setIsOfficial(Boolean isOfficial) {
    this.isOfficial = isOfficial;
  }

  public Merchant status(MerchantStatus status) {
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

  public Merchant verifiedAt(OffsetDateTime verifiedAt) {
    this.verifiedAt = verifiedAt;
    return this;
  }

  /**
   * Get verifiedAt
   * @return verifiedAt
   */
  @Valid 
  @Schema(name = "verifiedAt", example = "2026-04-03T12:00:00Z", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("verifiedAt")
  public OffsetDateTime getVerifiedAt() {
    return verifiedAt;
  }

  public void setVerifiedAt(OffsetDateTime verifiedAt) {
    this.verifiedAt = verifiedAt;
  }

  public Merchant createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
   */
  @NotNull @Valid 
  @Schema(name = "createdAt", example = "2026-04-03T10:00:00Z", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("createdAt")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Merchant updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   */
  @NotNull @Valid 
  @Schema(name = "updatedAt", example = "2026-04-03T10:10:00Z", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("updatedAt")
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Merchant merchant = (Merchant) o;
    return Objects.equals(this.id, merchant.id) &&
        Objects.equals(this.merchantCode, merchant.merchantCode) &&
        Objects.equals(this.shopName, merchant.shopName) &&
        Objects.equals(this.shopSlug, merchant.shopSlug) &&
        Objects.equals(this.legalName, merchant.legalName) &&
        Objects.equals(this.taxId, merchant.taxId) &&
        Objects.equals(this.email, merchant.email) &&
        Objects.equals(this.phoneNumber, merchant.phoneNumber) &&
        Objects.equals(this.supportEmail, merchant.supportEmail) &&
        Objects.equals(this.supportPhone, merchant.supportPhone) &&
        Objects.equals(this.description, merchant.description) &&
        Objects.equals(this.logoUrl, merchant.logoUrl) &&
        Objects.equals(this.coverImageUrl, merchant.coverImageUrl) &&
        Objects.equals(this.businessType, merchant.businessType) &&
        Objects.equals(this.registeredCountry, merchant.registeredCountry) &&
        Objects.equals(this.currency, merchant.currency) &&
        Objects.equals(this.commissionRate, merchant.commissionRate) &&
        Objects.equals(this.ratingAverage, merchant.ratingAverage) &&
        Objects.equals(this.isOfficial, merchant.isOfficial) &&
        Objects.equals(this.status, merchant.status) &&
        Objects.equals(this.verifiedAt, merchant.verifiedAt) &&
        Objects.equals(this.createdAt, merchant.createdAt) &&
        Objects.equals(this.updatedAt, merchant.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, merchantCode, shopName, shopSlug, legalName, taxId, email, phoneNumber, supportEmail, supportPhone, description, logoUrl, coverImageUrl, businessType, registeredCountry, currency, commissionRate, ratingAverage, isOfficial, status, verifiedAt, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Merchant {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
    sb.append("    ratingAverage: ").append(toIndentedString(ratingAverage)).append("\n");
    sb.append("    isOfficial: ").append(toIndentedString(isOfficial)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    verifiedAt: ").append(toIndentedString(verifiedAt)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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

