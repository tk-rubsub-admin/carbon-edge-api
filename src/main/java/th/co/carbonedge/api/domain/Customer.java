package th.co.carbonedge.api.domain;

import java.time.LocalDate;
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
import th.co.carbonedge.api.constant.CustomerStatus;
import th.co.carbonedge.api.constant.Gender;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "customers",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_customers_email", columnNames = "email"),
        @UniqueConstraint(name = "uk_customers_phone_number", columnNames = "phone_number")
    }
)
public class Customer {

    @Id
    @GeneratedValue(generator = "customerIdGenerator")
    @GenericGenerator(name = "customerIdGenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "CUS"),
            strategy = "th.co.carbonedge.api.repository.jpa.RandomIdGenerator")
    private String id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 50)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(length = 150)
    private String displayName;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private CustomerStatus status;

    @Column(nullable = false)
    private Boolean isEmailVerified;

    @Column(nullable = false)
    private Boolean isPhoneVerified;

    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "customer_addresses", joinColumns = @JoinColumn(name = "customer_id"))
    @OrderColumn(name = "address_order_index")
    private List<CustomerAddress> addresses = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void applyDefaults() {
        if (status == null) {
            status = CustomerStatus.PENDING_VERIFICATION;
        }
        if (isEmailVerified == null) {
            isEmailVerified = Boolean.FALSE;
        }
        if (isPhoneVerified == null) {
            isPhoneVerified = Boolean.FALSE;
        }
        if (addresses == null) {
            addresses = new ArrayList<>();
        }
    }
}
