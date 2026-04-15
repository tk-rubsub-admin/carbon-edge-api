package th.co.carbonedge.api.dto.customer;

import java.io.Serializable;

import jakarta.annotation.Generated;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated(value = "manual", date = "2026-04-03", comments = "Added from openapi.yml without modifying existing code")
public class UpdateCustomerAddressRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 100)
    private String label;

    @Size(max = 255)
    private String recipientName;

    private String recipientPhone;

    @Size(max = 255)
    private String line1;

    @Size(max = 255)
    private String line2;

    @Size(max = 20)
    private String thaiProvinceCode;

    @Size(max = 20)
    private String thaiAmphureCode;

    @Size(max = 20)
    private String thaiTumbonCode;

    @Size(min = 2, max = 2)
    private String countryCode;

    private Boolean isDefault;
}
