package th.co.carbonedge.api.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import th.co.carbonedge.api.constant.MerchantAddressType;
import th.co.carbonedge.api.constant.MerchantBusinessType;
import th.co.carbonedge.api.constant.MerchantStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "merchants",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_merchants_code", columnNames = "merchant_code"),
        @UniqueConstraint(name = "uk_merchants_shop_slug", columnNames = "shop_slug"),
        @UniqueConstraint(name = "uk_merchants_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_merchants_tax_id", columnNames = "tax_id")
    }
)
public class Merchant {

    @Id
    @GeneratedValue(generator = "merchantIdGenerator")
    @GenericGenerator(name = "merchantIdGenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "MCH"),
            strategy = "th.co.carbonedge.api.repository.jpa.RandomIdGenerator")
    private String id;

    @Column(nullable = false, length = 100)
    private String merchantCode;

    @Column(nullable = false, length = 255)
    private String shopName;

    @Column(nullable = false, length = 255)
    private String shopSlug;

    @Column(length = 255)
    private String legalName;

    @Column(length = 50)
    private String taxId;

    @Column(length = 255)
    private String email;

    @Column(length = 50)
    private String phoneNumber;

    @Column(length = 255)
    private String supportEmail;

    @Column(length = 50)
    private String supportPhone;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 1000)
    private String logoUrl;

    @Column(length = 1000)
    private String coverImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private MerchantBusinessType businessType;

    @Column(length = 2)
    private String registeredCountry;

    @Column(length = 3)
    private String currency;

    @Column(precision = 7, scale = 2)
    private BigDecimal commissionRate;

    @Column(precision = 4, scale = 2)
    private BigDecimal ratingAverage;

    @Column(nullable = false)
    private Boolean isOfficial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private MerchantStatus status;

    private OffsetDateTime verifiedAt;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "merchant_addresses", joinColumns = @JoinColumn(name = "merchant_id"))
    @OrderColumn(name = "address_order_index")
    private List<MerchantAddress> addresses = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void applyDefaults() {
        if (status == null) {
            status = MerchantStatus.PENDING_APPROVAL;
        }
        if (isOfficial == null) {
            isOfficial = Boolean.FALSE;
        }
        if (commissionRate == null) {
            commissionRate = BigDecimal.ZERO;
        }
        if (ratingAverage == null) {
            ratingAverage = BigDecimal.ZERO;
        }
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
    }
}
