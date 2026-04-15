package th.co.carbonedge.api.dto.wishlist;

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
public class CreateWishlistRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String customerId;
}
