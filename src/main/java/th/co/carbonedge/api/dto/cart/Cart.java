package th.co.carbonedge.api.dto.cart;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import th.co.carbonedge.api.constant.CartStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated(value = "manual", date = "2026-04-03", comments = "Added from openapi.yml without modifying existing code")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;

    private String customerId;

    @NotNull
    private CartStatus status;

    @Valid
    @NotNull
    private List<@Valid CartItem> items = new ArrayList<>();

    @NotNull
    @Min(0)
    private Double subtotal;

    @NotNull
    @Min(0)
    private Double discountAmount;

    @NotNull
    @Min(0)
    private Double totalAmount;

    @Min(0)
    private Integer totalItems;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    private OffsetDateTime updatedAt;
}
