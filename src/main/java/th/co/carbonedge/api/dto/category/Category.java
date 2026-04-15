package th.co.carbonedge.api.dto.category;

import java.io.Serializable;
import java.net.URI;
import java.time.OffsetDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import th.co.carbonedge.api.constant.CategoryStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Generated(value = "manual", date = "2026-04-03", comments = "Added from openapi.yml without modifying existing code")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Schema(example = "1")
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Schema(example = "Pet Perfume")
    private String name;

    @NotBlank
    @Size(max = 255)
    @Schema(example = "pet-perfume")
    private String slug;

    @Schema(example = "Perfume products for dogs and cats")
    private String description;

    @Schema(nullable = true, example = "null")
    private Long parentId;

    @Schema(example = "https://cdn.example.com/categories/pet-perfume.jpg")
    private URI imageUrl;

    @NotNull
    private CategoryStatus status;

    @PositiveOrZero
    @Schema(example = "1")
    private Integer sortOrder;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    private OffsetDateTime updatedAt;
}
