package th.co.carbonedge.api.dto.wishlist;

import java.io.Serializable;

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
public class MoveWishlistItemToCartRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(1)
    private Long cartId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
