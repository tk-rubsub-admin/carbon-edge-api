package th.co.carbonedge.api.dto.cart;

import java.io.Serializable;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated(value = "manual", date = "2026-04-03", comments = "Added from openapi.yml without modifying existing code")
public class CreateCartRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerId;
}
