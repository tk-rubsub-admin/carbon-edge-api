package th.co.carbonedge.api.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets MerchantStatus
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-04-16T22:01:36.911071+07:00[Asia/Bangkok]", comments = "Generator version: 7.8.0")
public enum MerchantStatus {
  
  PENDING_APPROVAL("PENDING_APPROVAL"),
  
  ACTIVE("ACTIVE"),
  
  SUSPENDED("SUSPENDED"),
  
  REJECTED("REJECTED"),
  
  INACTIVE("INACTIVE");

  private String value;

  MerchantStatus(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static MerchantStatus fromValue(String value) {
    for (MerchantStatus b : MerchantStatus.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

