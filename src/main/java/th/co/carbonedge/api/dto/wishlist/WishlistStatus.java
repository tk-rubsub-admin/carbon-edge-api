package th.co.carbonedge.api.dto.wishlist;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WishlistStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customerId;

    private Long productId;

    private Boolean wishlisted;

    private Long wishlistId;

    private Long wishlistItemId;
}
