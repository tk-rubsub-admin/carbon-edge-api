package th.co.carbonedge.api.dto.merchant;

import java.io.Serializable;

import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated(value = "manual", date = "2026-04-03", comments = "Added from openapi.yml without modifying existing code")
public class MerchantAddressResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    @NotNull
    private MerchantAddress data;
}
