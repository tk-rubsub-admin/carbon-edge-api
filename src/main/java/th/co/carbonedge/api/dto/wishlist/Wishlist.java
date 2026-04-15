package th.co.carbonedge.api.dto.wishlist;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Wishlist implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String customerId;

    private Integer totalItems;

    private List<WishlistItem> items;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
