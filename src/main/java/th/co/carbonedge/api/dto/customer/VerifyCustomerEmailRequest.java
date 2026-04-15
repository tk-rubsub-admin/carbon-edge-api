package th.co.carbonedge.api.dto.customer;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VerifyCustomerEmailRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String token;
}
