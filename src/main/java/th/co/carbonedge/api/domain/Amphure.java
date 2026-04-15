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
@Table(name = "thai_amphures")
public class Amphure {

    @Id
    @Column(length = 20)
    private String code;

    @Column(name = "thai_province_code", nullable = false, length = 20)
    private String thaiProvinceCode;

    @Column(name = "name_th", nullable = false, length = 150)
    private String nameTh;

    @Column(name = "name_en", length = 150)
    private String nameEn;
}
