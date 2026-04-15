package th.co.carbonedge.api.dto.auth;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import th.co.carbonedge.api.constant.UserStatus;
import th.co.carbonedge.api.dto.customer.Customer;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthenticatedUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String customerId;

    private Long wishlistId;

    private Long cartId;

    private String email;

    private UserStatus status;

    private OffsetDateTime lastLoginAt;

    private Customer customer;
}
