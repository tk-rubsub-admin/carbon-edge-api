package th.co.carbonedge.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProductImage {

    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_url", nullable = false, length = 1000)
    private String url;

    @Column(name = "alt_text", length = 500)
    private String altText;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;
}
