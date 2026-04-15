package th.co.carbonedge.api.controller.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import th.co.carbonedge.api.constant.CustomerStatus;
import th.co.carbonedge.api.dto.customer.CreateCustomerAddressRequest;
import th.co.carbonedge.api.dto.customer.CreateCustomerRequest;
import th.co.carbonedge.api.dto.customer.CustomerAddressListResponse;
import th.co.carbonedge.api.dto.customer.CustomerAddressResponse;
import th.co.carbonedge.api.dto.customer.CustomerPageResponse;
import th.co.carbonedge.api.dto.customer.CustomerResponse;
import th.co.carbonedge.api.dto.customer.UpdateCustomerAddressRequest;
import th.co.carbonedge.api.dto.customer.UpdateCustomerRequest;
import th.co.carbonedge.api.dto.customer.VerifyCustomerEmailRequest;
import th.co.carbonedge.api.exception.InvalidRequestException;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.exception.TokenExpiredException;
import th.co.carbonedge.api.service.CustomerService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customers")
@Tag(name = "Customers", description = "Customer management APIs")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    @GetMapping
    public CustomerPageResponse listCustomers(
        @RequestParam(defaultValue = "0") @Min(0) Integer page,
        @RequestParam(defaultValue = "20") @Min(1) Integer size,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) CustomerStatus status,
        @RequestParam(required = false) Boolean isEmailVerified,
        @RequestParam(required = false) Boolean isPhoneVerified
    ) {
        return customerService.listCustomers(page, size, keyword, status, isEmailVerified, isPhoneVerified);
    }

    @GetMapping("/{customerId}")
    public CustomerResponse getCustomerById(@PathVariable @Min(1) String customerId) throws DataNotFoundException {
        return customerService.getCustomerById(customerId);
    }

    @PutMapping("/{customerId}")
    public CustomerResponse updateCustomer(
        @PathVariable @Min(1) String customerId,
        @Valid @RequestBody UpdateCustomerRequest request
    ) throws DataNotFoundException {
        return customerService.updateCustomer(customerId, request);
    }

    @PostMapping("/verify-email")
    public CustomerResponse verifyCustomerEmail(@Valid @RequestBody VerifyCustomerEmailRequest request)
        throws DataNotFoundException, InvalidRequestException, TokenExpiredException {
        return customerService.verifyCustomerEmail(request);
    }

    @PostMapping("/{customerId}/resend-verification-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resendVerificationEmail(@PathVariable @Min(1) String customerId)
        throws DataNotFoundException, InvalidRequestException {
        customerService.resendCustomerEmailVerification(customerId);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable @Min(1) String customerId) throws DataNotFoundException {
        customerService.deleteCustomer(customerId);
    }

    @GetMapping("/{customerId}/addresses")
    public CustomerAddressListResponse listCustomerAddresses(@PathVariable @Min(1) String customerId)
        throws DataNotFoundException {
        return customerService.listCustomerAddresses(customerId);
    }

    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<CustomerAddressResponse> createCustomerAddress(
        @PathVariable @Min(1) String customerId,
        @Valid @RequestBody CreateCustomerAddressRequest request
    ) throws DataNotFoundException, InvalidRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomerAddress(customerId, request));
    }

    @PutMapping("/{customerId}/addresses/{addressId}")
    public CustomerAddressResponse updateCustomerAddress(
        @PathVariable @Min(1) String customerId,
        @PathVariable @Min(1) Long addressId,
        @Valid @RequestBody UpdateCustomerAddressRequest request
    ) throws DataNotFoundException, InvalidRequestException {
        return customerService.updateCustomerAddress(customerId, addressId, request);
    }

    @DeleteMapping("/{customerId}/addresses/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerAddress(
        @PathVariable @Min(1) String customerId,
        @PathVariable @Min(1) Long addressId
    ) throws DataNotFoundException {
        customerService.deleteCustomerAddress(customerId, addressId);
    }
}
