package th.co.carbonedge.api.dto.merchant;

import java.io.Serializable;
import java.net.URI;

import jakarta.annotation.Generated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import th.co.carbonedge.api.constant.MerchantBusinessType;
import th.co.carbonedge.api.constant.MerchantStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated(value = "manual", date = "2026-04-03", comments = "Added from openapi.yml without modifying existing code")
public class CreateMerchantRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 100)
    private String merchantCode;

    @NotBlank
    @Size(max = 255)
    private String shopName;

    @NotBlank
    @Size(max = 255)
    private String shopSlug;

    @Size(max = 255)
    private String legalName;

    @Size(max = 50)
    private String taxId;

    @Email
    private String email;

    private String phoneNumber;

    @Email
    private String supportEmail;

    private String supportPhone;

    private String description;

    private URI logoUrl;

    private URI coverImageUrl;

    private MerchantBusinessType businessType;

    @Size(min = 2, max = 2)
    private String registeredCountry;

    @Size(min = 3, max = 3)
    private String currency;

    @Min(0)
    private Double commissionRate;

    private Boolean isOfficial = Boolean.FALSE;

    @NotNull
    private MerchantStatus status;
}
