package th.co.carbonedge.api.service;

import java.time.OffsetDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.carbonedge.api.constant.CartStatus;
import th.co.carbonedge.api.constant.CustomerStatus;
import th.co.carbonedge.api.constant.ErrorCode;
import th.co.carbonedge.api.constant.UserStatus;
import th.co.carbonedge.api.domain.Cart;
import th.co.carbonedge.api.domain.Customer;
import th.co.carbonedge.api.domain.User;
import th.co.carbonedge.api.dto.auth.AuthenticatedUser;
import th.co.carbonedge.api.dto.auth.AuthenticatedUserResponse;
import th.co.carbonedge.api.dto.auth.LoginRequest;
import th.co.carbonedge.api.dto.auth.LoginResponse;
import th.co.carbonedge.api.dto.auth.RefreshAccessTokenRequest;
import th.co.carbonedge.api.repository.CartRepository;
import th.co.carbonedge.api.repository.WishlistRepository;
import th.co.carbonedge.api.exception.InvalidRequestException;
import th.co.carbonedge.api.repository.CustomerRepository;
import th.co.carbonedge.api.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenService authTokenService;
    private final WishlistRepository wishlistRepository;
    private final CartRepository cartRepository;

    public LoginResponse login(LoginRequest request) throws InvalidRequestException {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> invalidCredentials());
        Customer customer = validateUserAndLoadCustomer(user);

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw invalidCredentials();
        }

        return buildLoginResponse(user, customer, OffsetDateTime.now());
    }

    public LoginResponse refreshAccessToken(RefreshAccessTokenRequest request) throws InvalidRequestException {
        AuthTokenService.TokenClaims tokenClaims = authTokenService.parseAndValidate(request.getAccessToken());

        User user = userRepository.findById(tokenClaims.userId())
            .orElseThrow(() -> new InvalidRequestException(ErrorCode.DATA_NOT_FOUND, "User for token was not found", HttpStatus.UNAUTHORIZED));

        Customer customer = validateUserAndLoadCustomer(user);
        return buildLoginResponse(user, customer, OffsetDateTime.now());
    }

    public AuthenticatedUserResponse getAuthenticatedUser(AuthTokenService.TokenClaims tokenClaims) throws InvalidRequestException {
        User user = userRepository.findById(tokenClaims.userId())
            .orElseThrow(() -> new InvalidRequestException(ErrorCode.DATA_NOT_FOUND, "User for token was not found", HttpStatus.UNAUTHORIZED));

        Customer customer = validateUserAndLoadCustomer(user);
        return new AuthenticatedUserResponse().setData(buildAuthenticatedUser(user, customer, tokenClaims.issuedAt()));
    }

    private Customer validateUserAndLoadCustomer(User user) throws InvalidRequestException {
        if (user.getStatus() == UserStatus.INACTIVE || user.getStatus() == UserStatus.SUSPENDED) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST, "User is not allowed to login", HttpStatus.UNAUTHORIZED);
        }

        Customer customer = customerRepository.findById(user.getCustomerId())
            .orElseThrow(() -> new InvalidRequestException(ErrorCode.DATA_NOT_FOUND, "Customer for user was not found", HttpStatus.UNAUTHORIZED));

        if (customer.getStatus() == CustomerStatus.INACTIVE || customer.getStatus() == CustomerStatus.SUSPENDED) {
            throw new InvalidRequestException(ErrorCode.INVALID_REQUEST, "Customer is not allowed to login", HttpStatus.UNAUTHORIZED);
        }
        return customer;
    }

    private LoginResponse buildLoginResponse(User user, Customer customer, OffsetDateTime lastLoginAt) {
        AuthTokenService.AuthToken authToken = authTokenService.generateToken(user);

        return new LoginResponse()
            .setTokenType(authToken.tokenType())
            .setAccessToken(authToken.accessToken())
            .setExpiresAt(authToken.expiresAt())
            .setData(buildAuthenticatedUser(user, customer, lastLoginAt));
    }

    private AuthenticatedUser buildAuthenticatedUser(User user, Customer customer, OffsetDateTime lastLoginAt) {
        return new AuthenticatedUser()
            .setUserId(user.getId())
            .setCustomerId(user.getCustomerId())
            .setWishlistId(wishlistRepository.findByCustomerId(customer.getId()).map(th.co.carbonedge.api.domain.Wishlist::getId).orElse(null))
            .setCartId(cartRepository.findByCustomerIdAndStatus(customer.getId(), CartStatus.ACTIVE).map(Cart::getId).orElse(null))
            .setEmail(user.getEmail())
            .setStatus(user.getStatus())
            .setLastLoginAt(lastLoginAt)
            .setCustomer(toCustomerDto(customer));
    }

    private InvalidRequestException invalidCredentials() {
        return new InvalidRequestException(ErrorCode.INVALID_REQUEST, "Invalid email or password", HttpStatus.UNAUTHORIZED);
    }

    private th.co.carbonedge.api.dto.customer.Customer toCustomerDto(Customer customer) {
        return new th.co.carbonedge.api.dto.customer.Customer()
            .setId(customer.getId())
            .setEmail(customer.getEmail())
            .setPhoneNumber(customer.getPhoneNumber())
            .setFirstName(customer.getFirstName())
            .setLastName(customer.getLastName())
            .setDisplayName(customer.getDisplayName())
            .setDateOfBirth(customer.getDateOfBirth())
            .setGender(customer.getGender())
            .setStatus(customer.getStatus())
            .setIsEmailVerified(customer.getIsEmailVerified())
            .setIsPhoneVerified(customer.getIsPhoneVerified())
            .setCreatedAt(customer.getCreatedAt())
            .setUpdatedAt(customer.getUpdatedAt());
    }
}
