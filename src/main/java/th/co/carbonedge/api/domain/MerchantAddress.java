package th.co.carbonedge.api.domain;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import th.co.carbonedge.api.constant.MerchantAddressType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MerchantAddress {

    @Column(name = "address_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type", nullable = false, length = 32)
    private MerchantAddressType addressType;

    @Column(name = "recipient_name", length = 255)
    private String recipientName;

    @Column(name = "recipient_phone", length = 50)
    private String recipientPhone;

    @Column(name = "line1", nullable = false, length = 255)
    private String line1;

    @Column(name = "line2", length = 255)
    private String line2;

    @Column(name = "thai_tumbon_code", length = 20)
    private String thaiTumbonCode;

    @Column(name = "thai_amphure_code", nullable = false, length = 20)
    private String thaiAmphureCode;

    @Column(name = "thai_province_code", nullable = false, length = 20)
    private String thaiProvinceCode;

    @Column(name = "country_code", nullable = false, length = 2)
    private String countryCode;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
