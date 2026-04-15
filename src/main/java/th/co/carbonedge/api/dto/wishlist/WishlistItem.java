package th.co.carbonedge.api.dto.wishlist;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WishlistItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long productId;

    private String productName;

    private String sku;

    private String thumbnailUrl;

    private Double price;

    private String currency;

    private OffsetDateTime addedAt;
}
