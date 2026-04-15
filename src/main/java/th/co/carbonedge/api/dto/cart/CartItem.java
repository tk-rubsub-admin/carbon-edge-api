package th.co.carbonedge.api.dto.cart;

import java.io.Serializable;
import java.net.URI;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class CartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;

    @NotNull
    private Long productId;

    @NotBlank
    @Schema(example = "Paw de Fume Cloudy")
    private String productName;

    @NotBlank
    @Schema(example = "PDF-CLOUDY-30ML")
    private String sku;

    private URI thumbnailUrl;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    @Min(0)
    private Double unitPrice;

    @NotNull
    @Min(0)
    private Double lineTotal;
}
