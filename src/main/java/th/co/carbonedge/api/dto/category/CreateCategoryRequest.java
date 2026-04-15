package th.co.carbonedge.api.dto.category;

import java.io.Serializable;
import java.net.URI;

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
public class CreateCategoryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String slug;

    private String description;

    @Schema(nullable = true)
    private Long parentId;

    private URI imageUrl;

    @NotNull
    private CategoryStatus status;

    @PositiveOrZero
    private Integer sortOrder = 0;
}
