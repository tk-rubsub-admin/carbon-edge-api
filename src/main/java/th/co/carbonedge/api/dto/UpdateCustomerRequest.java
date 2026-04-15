package th.co.carbonedge.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import th.co.carbonedge.api.dto.CustomerStatus;
import th.co.carbonedge.api.dto.Gender;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * UpdateCustomerRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-15T22:52:19.015873+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public class UpdateCustomerRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String email;

  private String phoneNumber;

  private String firstName;

  private String lastName;

  private String displayName;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateOfBirth;

  private Gender gender;

  private CustomerStatus status;

  private Boolean isEmailVerified;

  private Boolean isPhoneVerified;

  public UpdateCustomerRequest email(String email) {
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

  public UpdateCustomerRequest phoneNumber(String phoneNumber) {
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

  public UpdateCustomerRequest firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   */
  @Size(max = 100) 
  @Schema(name = "firstName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public UpdateCustomerRequest lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   */
  @Size(max = 100) 
  @Schema(name = "lastName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public UpdateCustomerRequest displayName(String displayName) {
    this.displayName = displayName;
    return this;
  }

  /**
   * Get displayName
   * @return displayName
   */
  @Size(max = 150) 
  @Schema(name = "displayName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("displayName")
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public UpdateCustomerRequest dateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
    return this;
  }

  /**
   * Get dateOfBirth
   * @return dateOfBirth
   */
  @Valid 
  @Schema(name = "dateOfBirth", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("dateOfBirth")
  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public UpdateCustomerRequest gender(Gender gender) {
    this.gender = gender;
    return this;
  }

  /**
   * Get gender
   * @return gender
   */
  @Valid 
  @Schema(name = "gender", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("gender")
  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public UpdateCustomerRequest status(CustomerStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @Valid 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public CustomerStatus getStatus() {
    return status;
  }

  public void setStatus(CustomerStatus status) {
    this.status = status;
  }

  public UpdateCustomerRequest isEmailVerified(Boolean isEmailVerified) {
    this.isEmailVerified = isEmailVerified;
    return this;
  }

  /**
   * Get isEmailVerified
   * @return isEmailVerified
   */
  
  @Schema(name = "isEmailVerified", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isEmailVerified")
  public Boolean getIsEmailVerified() {
    return isEmailVerified;
  }

  public void setIsEmailVerified(Boolean isEmailVerified) {
    this.isEmailVerified = isEmailVerified;
  }

  public UpdateCustomerRequest isPhoneVerified(Boolean isPhoneVerified) {
    this.isPhoneVerified = isPhoneVerified;
    return this;
  }

  /**
   * Get isPhoneVerified
   * @return isPhoneVerified
   */
  
  @Schema(name = "isPhoneVerified", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("isPhoneVerified")
  public Boolean getIsPhoneVerified() {
    return isPhoneVerified;
  }

  public void setIsPhoneVerified(Boolean isPhoneVerified) {
    this.isPhoneVerified = isPhoneVerified;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateCustomerRequest updateCustomerRequest = (UpdateCustomerRequest) o;
    return Objects.equals(this.email, updateCustomerRequest.email) &&
        Objects.equals(this.phoneNumber, updateCustomerRequest.phoneNumber) &&
        Objects.equals(this.firstName, updateCustomerRequest.firstName) &&
        Objects.equals(this.lastName, updateCustomerRequest.lastName) &&
        Objects.equals(this.displayName, updateCustomerRequest.displayName) &&
        Objects.equals(this.dateOfBirth, updateCustomerRequest.dateOfBirth) &&
        Objects.equals(this.gender, updateCustomerRequest.gender) &&
        Objects.equals(this.status, updateCustomerRequest.status) &&
        Objects.equals(this.isEmailVerified, updateCustomerRequest.isEmailVerified) &&
        Objects.equals(this.isPhoneVerified, updateCustomerRequest.isPhoneVerified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email, phoneNumber, firstName, lastName, displayName, dateOfBirth, gender, status, isEmailVerified, isPhoneVerified);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateCustomerRequest {\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
    sb.append("    dateOfBirth: ").append(toIndentedString(dateOfBirth)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    isEmailVerified: ").append(toIndentedString(isEmailVerified)).append("\n");
    sb.append("    isPhoneVerified: ").append(toIndentedString(isPhoneVerified)).append("\n");
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

