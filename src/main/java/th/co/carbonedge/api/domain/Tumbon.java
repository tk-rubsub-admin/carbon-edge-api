package th.co.carbonedge.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thai_tumbons")
public class Tumbon {

    @Id
    @Column(length = 20)
    private String code;

    @Column(name = "thai_amphure_code", nullable = false, length = 20)
    private String thaiAmphureCode;

    @Column(name = "name_th", nullable = false, length = 150)
    private String nameTh;

    @Column(name = "name_en", length = 150)
    private String nameEn;

    @Column(name = "postal_code", length = 20)
    private String postalCode;
}
