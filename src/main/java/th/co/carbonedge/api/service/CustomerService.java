package th.co.carbonedge.api.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.carbonedge.api.constant.CustomerStatus;
import th.co.carbonedge.api.domain.Customer;
import th.co.carbonedge.api.domain.CustomerAddress;
import th.co.carbonedge.api.domain.CustomerEmailVerificationToken;
import th.co.carbonedge.api.domain.Wishlist;
import th.co.carbonedge.api.dto.customer.CreateCustomerAddressRequest;
import th.co.carbonedge.api.dto.customer.CreateCustomerRequest;
import th.co.carbonedge.api.dto.customer.CustomerAddressListResponse;
import th.co.carbonedge.api.dto.customer.CustomerAddressResponse;
import th.co.carbonedge.api.dto.customer.CustomerPageResponse;
import th.co.carbonedge.api.dto.customer.CustomerResponse;
import th.co.carbonedge.api.dto.customer.UpdateCustomerAddressRequest;
import th.co.carbonedge.api.dto.customer.UpdateCustomerRequest;
import th.co.carbonedge.api.dto.customer.VerifyCustomerEmailRequest;
import th.co.carbonedge.api.exception.DataConflictException;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.exception.InvalidRequestException;
import th.co.carbonedge.api.exception.TokenExpiredException;
import th.co.carbonedge.api.notifier.CustomerEmailVerificationNotifier;
import th.co.carbonedge.api.repository.AmphureRepository;
import th.co.carbonedge.api.repository.CustomerEmailVerificationTokenRepository;
import th.co.carbonedge.api.repository.CustomerRepository;
import th.co.carbonedge.api.repository.ProvinceRepository;
import th.co.carbonedge.api.repository.TumbonRepository;
import th.co.carbonedge.api.repository.WishlistRepository;
import th.co.carbonedge.api.specification.CustomerSpecification;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private static final long EMAIL_VERIFICATION_EXPIRE_HOURS = 24L;

    private final CustomerRepository customerRepository;
    private final CustomerEmailVerificationTokenRepository customerEmailVerificationTokenRepository;
    private final CustomerEmailVerificationNotifier customerEmailVerificationNotifier;
    private final UserService userService;
    private final ProvinceRepository provinceRepository;
    private final AmphureRepository amphureRepository;
    private final TumbonRepository tumbonRepository;
    private final WishlistRepository wishlistRepository;

    @Transactional(readOnly = true)
    public CustomerPageResponse listCustomers(
        Integer page,
        Integer size,
        String keyword,
        CustomerStatus status,
        Boolean isEmailVerified,
        Boolean isPhoneVerified
    ) {
        Pageable pageable = PageRequest.of(
            page == null ? 0 : page,
            size == null ? 20 : size,
            Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Customer> result = customerRepository.findAll(
            CustomerSpecification.withFilters(keyword, status, isEmailVerified, isPhoneVerified),
            pageable
        );

        return new CustomerPageResponse()
            .setData(result.getContent().stream().map(this::toDto).toList())
            .setPage(result.getNumber())
            .setSize(result.getSize())
            .setTotalElements(result.getTotalElements())
            .setTotalPages(result.getTotalPages())
            .setHasNext(result.hasNext());
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(String customerId) throws DataNotFoundException {
        return new CustomerResponse().setData(toDto(findActiveCustomer(customerId)));
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        validateUniqueness(request.getEmail(), request.getPhoneNumber(), null);
        Customer customer = new Customer();
        applyCreateRequest(customer, request);
        Customer savedCustomer = customerRepository.save(customer);
        userService.createUserForCustomer(savedCustomer, request.getPassword());
        wishlistRepository.save(Wishlist.builder()
            .customerId(savedCustomer.getId())
            .build());
        issueEmailVerificationToken(savedCustomer);
        return new CustomerResponse().setData(toDto(savedCustomer));
    }

    public CustomerResponse updateCustomer(String customerId, UpdateCustomerRequest request) throws DataNotFoundException {
        Customer customer = findActiveCustomer(customerId);
        validateUniqueness(request.getEmail(), request.getPhoneNumber(), customerId);
        applyUpdateRequest(customer, request);
        Customer savedCustomer = customerRepository.save(customer);
        userService.syncUserForCustomer(savedCustomer);
        return new CustomerResponse().setData(toDto(savedCustomer));
    }

    public CustomerResponse verifyCustomerEmail(VerifyCustomerEmailRequest request)
        throws DataNotFoundException, InvalidRequestException, TokenExpiredException {
        CustomerEmailVerificationToken token = customerEmailVerificationTokenRepository
            .findByTokenHashAndUsedAtIsNull(hashToken(request.getToken()))
            .orElseThrow(() -> new InvalidRequestException("Email verification token is invalid"));

        if (token.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new TokenExpiredException("Email verification token has expired");
        }

        Customer customer = findActiveCustomer(token.getCustomerId());
        customer.setIsEmailVerified(Boolean.TRUE);
        if (customer.getStatus() == CustomerStatus.PENDING_VERIFICATION) {
            customer.setStatus(CustomerStatus.ACTIVE);
        }

        token.setUsedAt(OffsetDateTime.now());
        customerEmailVerificationTokenRepository.save(token);
        return new CustomerResponse().setData(toDto(customerRepository.save(customer)));
    }

    public void resendCustomerEmailVerification(String customerId) throws DataNotFoundException, InvalidRequestException {
        Customer customer = findActiveCustomer(customerId);
        if (Boolean.TRUE.equals(customer.getIsEmailVerified())) {
            throw new InvalidRequestException("Customer email is already verified");
        }
        issueEmailVerificationToken(customer);
    }

    public void deleteCustomer(String customerId) throws DataNotFoundException {
        Customer customer = findActiveCustomer(customerId);
        customer.setStatus(CustomerStatus.INACTIVE);
        Customer savedCustomer = customerRepository.save(customer);
        userService.syncUserForCustomer(savedCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerAddressListResponse listCustomerAddresses(String customerId) throws DataNotFoundException {
        Customer customer = findActiveCustomer(customerId);
        List<th.co.carbonedge.api.dto.customer.CustomerAddress> addresses = customer.getAddresses().stream()
            .map(address -> toAddressDto(customer.getId(), address))
            .toList();
        return new CustomerAddressListResponse().setData(addresses);
    }

    public CustomerAddressResponse createCustomerAddress(String customerId, CreateCustomerAddressRequest request)
        throws DataNotFoundException, InvalidRequestException {
        Customer customer = findActiveCustomer(customerId);
        validateThaiAddressCodes(
            request.getThaiProvinceCode(),
            request.getThaiAmphureCode(),
            request.getThaiTumbonCode()
        );
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            clearDefaultAddress(customer);
        }

        CustomerAddress address = CustomerAddress.builder()
            .id(nextAddressId(customer.getAddresses()))
            .label(request.getLabel())
            .recipientName(request.getRecipientName())
            .recipientPhone(request.getRecipientPhone())
            .line1(request.getLine1())
            .line2(request.getLine2())
            .thaiProvinceCode(request.getThaiProvinceCode())
            .thaiAmphureCode(request.getThaiAmphureCode())
            .thaiTumbonCode(request.getThaiTumbonCode())
            .countryCode(request.getCountryCode())
            .isDefault(Boolean.TRUE.equals(request.getIsDefault()))
            .build();

        customer.getAddresses().add(address);
        Customer savedCustomer = customerRepository.save(customer);
        CustomerAddress savedAddress = findAddress(savedCustomer, address.getId());
        return new CustomerAddressResponse().setData(toAddressDto(savedCustomer.getId(), savedAddress));
    }

    public CustomerAddressResponse updateCustomerAddress(
        String customerId,
        Long addressId,
        UpdateCustomerAddressRequest request
    ) throws DataNotFoundException, InvalidRequestException {
        Customer customer = findActiveCustomer(customerId);
        CustomerAddress address = findAddress(customer, addressId);

        if (request.getLabel() != null) {
            address.setLabel(request.getLabel());
        }
        if (request.getRecipientName() != null) {
            address.setRecipientName(request.getRecipientName());
        }
        if (request.getRecipientPhone() != null) {
            address.setRecipientPhone(request.getRecipientPhone());
        }
        if (request.getLine1() != null) {
            address.setLine1(request.getLine1());
        }
        if (request.getLine2() != null) {
            address.setLine2(request.getLine2());
        }
        if (request.getThaiProvinceCode() != null) {
            address.setThaiProvinceCode(request.getThaiProvinceCode());
        }
        if (request.getThaiAmphureCode() != null) {
            address.setThaiAmphureCode(request.getThaiAmphureCode());
        }
        if (request.getThaiTumbonCode() != null) {
            address.setThaiTumbonCode(request.getThaiTumbonCode());
        }
        if (request.getCountryCode() != null) {
            address.setCountryCode(request.getCountryCode());
        }
        validateThaiAddressCodes(
            address.getThaiProvinceCode(),
            address.getThaiAmphureCode(),
            address.getThaiTumbonCode()
        );
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            clearDefaultAddress(customer);
            address.setIsDefault(Boolean.TRUE);
        } else if (request.getIsDefault() != null) {
            address.setIsDefault(request.getIsDefault());
        }

        Customer savedCustomer = customerRepository.save(customer);
        CustomerAddress savedAddress = findAddress(savedCustomer, addressId);
        return new CustomerAddressResponse().setData(toAddressDto(savedCustomer.getId(), savedAddress));
    }

    public void deleteCustomerAddress(String customerId, Long addressId) throws DataNotFoundException {
        Customer customer = findActiveCustomer(customerId);
        CustomerAddress address = findAddress(customer, addressId);
        customer.getAddresses().remove(address);
        customerRepository.save(customer);
    }

    private Customer findActiveCustomer(String customerId) throws DataNotFoundException {
        return customerRepository.findByIdAndStatusNot(customerId, CustomerStatus.INACTIVE)
            .orElseThrow(() -> new DataNotFoundException("Customer " + customerId + " not found"));
    }

    private CustomerAddress findAddress(Customer customer, Long addressId) throws DataNotFoundException {
        return customer.getAddresses().stream()
            .filter(address -> addressId.equals(address.getId()))
            .findFirst()
            .orElseThrow(() -> new DataNotFoundException("Customer address " + addressId + " not found"));
    }

    private Long nextAddressId(List<CustomerAddress> addresses) {
        return addresses.stream()
            .map(CustomerAddress::getId)
            .filter(id -> id != null)
            .max(Comparator.naturalOrder())
            .orElse(0L) + 1;
    }

    private void clearDefaultAddress(Customer customer) {
        customer.getAddresses().forEach(address -> address.setIsDefault(Boolean.FALSE));
    }

    private void validateThaiAddressCodes(
        String thaiProvinceCode,
        String thaiAmphureCode,
        String thaiTumbonCode
    ) throws InvalidRequestException {
        provinceRepository.findById(thaiProvinceCode)
            .orElseThrow(() -> new InvalidRequestException("Thai province code is invalid: " + thaiProvinceCode));

        var amphure = amphureRepository.findById(thaiAmphureCode)
            .orElseThrow(() -> new InvalidRequestException("Thai amphure code is invalid: " + thaiAmphureCode));
        if (!thaiProvinceCode.equals(amphure.getThaiProvinceCode())) {
            throw new InvalidRequestException("Thai amphure code does not belong to thai province code");
        }

        if (thaiTumbonCode == null || thaiTumbonCode.isBlank()) {
            return;
        }

        var tumbon = tumbonRepository.findById(thaiTumbonCode)
            .orElseThrow(() -> new InvalidRequestException("Thai tumbon code is invalid: " + thaiTumbonCode));
        if (!thaiAmphureCode.equals(tumbon.getThaiAmphureCode())) {
            throw new InvalidRequestException("Thai tumbon code does not belong to thai amphure code");
        }
    }

    private void validateUniqueness(String email, String phoneNumber, String currentCustomerId) {
        if (email != null && !email.isBlank()) {
            boolean exists = currentCustomerId == null
                ? customerRepository.existsByEmail(email)
                : customerRepository.existsByEmailAndIdNot(email, currentCustomerId);
            if (exists) {
                throw new DataConflictException("Customer email already exists: " + email);
            }
        }
        if (phoneNumber != null && !phoneNumber.isBlank()) {
            boolean exists = currentCustomerId == null
                ? customerRepository.existsByPhoneNumber(phoneNumber)
                : customerRepository.existsByPhoneNumberAndIdNot(phoneNumber, currentCustomerId);
            if (exists) {
                throw new DataConflictException("Customer phone already exists: " + phoneNumber);
            }
        }
    }

    private void applyCreateRequest(Customer customer, CreateCustomerRequest request) {
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDisplayName(request.getDisplayName());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setGender(request.getGender());
        customer.setStatus(CustomerStatus.PENDING_VERIFICATION);
    }

    private void issueEmailVerificationToken(Customer customer) {
        customerEmailVerificationTokenRepository.findAllByCustomerIdAndUsedAtIsNull(customer.getId())
            .forEach(token -> token.setUsedAt(OffsetDateTime.now()));

        String rawToken = UUID.randomUUID().toString() + UUID.randomUUID();
        OffsetDateTime expiresAt = OffsetDateTime.now().plusHours(EMAIL_VERIFICATION_EXPIRE_HOURS);

        CustomerEmailVerificationToken token = CustomerEmailVerificationToken.builder()
            .customerId(customer.getId())
            .tokenHash(hashToken(rawToken))
            .expiresAt(expiresAt)
            .build();

        customerEmailVerificationTokenRepository.save(token);
        customerEmailVerificationNotifier.sendVerificationEmail(customer, rawToken, expiresAt);
    }

    private void applyUpdateRequest(Customer customer, UpdateCustomerRequest request) {
        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            customer.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getFirstName() != null) {
            customer.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }
        if (request.getDisplayName() != null) {
            customer.setDisplayName(request.getDisplayName());
        }
        if (request.getDateOfBirth() != null) {
            customer.setDateOfBirth(request.getDateOfBirth());
        }
        if (request.getGender() != null) {
            customer.setGender(request.getGender());
        }
        if (request.getStatus() != null) {
            customer.setStatus(request.getStatus());
        }
        if (request.getIsEmailVerified() != null) {
            customer.setIsEmailVerified(request.getIsEmailVerified());
        }
        if (request.getIsPhoneVerified() != null) {
            customer.setIsPhoneVerified(request.getIsPhoneVerified());
        }
    }

    private th.co.carbonedge.api.dto.customer.Customer toDto(Customer customer) {
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

    private th.co.carbonedge.api.dto.customer.CustomerAddress toAddressDto(String customerId, CustomerAddress address) {
        return new th.co.carbonedge.api.dto.customer.CustomerAddress()
            .setId(address.getId())
            .setCustomerId(customerId)
            .setLabel(address.getLabel())
            .setRecipientName(address.getRecipientName())
            .setRecipientPhone(address.getRecipientPhone())
            .setLine1(address.getLine1())
            .setLine2(address.getLine2())
            .setThaiProvinceCode(address.getThaiProvinceCode())
            .setThaiAmphureCode(address.getThaiAmphureCode())
            .setThaiTumbonCode(address.getThaiTumbonCode())
            .setCountryCode(address.getCountryCode())
            .setIsDefault(address.getIsDefault())
            .setCreatedAt(address.getCreatedAt())
            .setUpdatedAt(address.getUpdatedAt());
    }

    private String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(rawToken.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is not available", exception);
        }
    }

}
