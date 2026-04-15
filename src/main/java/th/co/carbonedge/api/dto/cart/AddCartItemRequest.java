package th.co.carbonedge.api.dto.cart;

import java.io.Serializable;

import jakarta.annotation.Generated;
import jakarta.validation.constraints.Min;
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
public class AddCartItemRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
