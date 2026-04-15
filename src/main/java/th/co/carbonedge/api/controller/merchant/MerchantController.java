package th.co.carbonedge.api.controller.merchant;

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
import th.co.carbonedge.api.constant.MerchantBusinessType;
import th.co.carbonedge.api.constant.MerchantStatus;
import th.co.carbonedge.api.dto.merchant.CreateMerchantAddressRequest;
import th.co.carbonedge.api.dto.merchant.CreateMerchantRequest;
import th.co.carbonedge.api.dto.merchant.MerchantAddressListResponse;
import th.co.carbonedge.api.dto.merchant.MerchantAddressResponse;
import th.co.carbonedge.api.dto.merchant.MerchantPageResponse;
import th.co.carbonedge.api.dto.merchant.MerchantResponse;
import th.co.carbonedge.api.dto.merchant.UpdateMerchantAddressRequest;
import th.co.carbonedge.api.dto.merchant.UpdateMerchantRequest;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.exception.InvalidRequestException;
import th.co.carbonedge.api.service.MerchantService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/merchants")
@Tag(name = "Merchants", description = "Merchant management APIs")
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping
    public ResponseEntity<MerchantResponse> createMerchant(@Valid @RequestBody CreateMerchantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(merchantService.createMerchant(request));
    }

    @GetMapping
    public MerchantPageResponse listMerchants(
        @RequestParam(defaultValue = "0") @Min(0) Integer page,
        @RequestParam(defaultValue = "20") @Min(1) Integer size,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) MerchantStatus status,
        @RequestParam(required = false) MerchantBusinessType businessType,
        @RequestParam(required = false) Boolean isOfficial
    ) {
        return merchantService.listMerchants(page, size, keyword, status, businessType, isOfficial);
    }

    @GetMapping("/{merchantId}")
    public MerchantResponse getMerchantById(@PathVariable String merchantId) throws DataNotFoundException {
        return merchantService.getMerchantById(merchantId);
    }

    @PutMapping("/{merchantId}")
    public MerchantResponse updateMerchant(
        @PathVariable @Min(1) String merchantId,
        @Valid @RequestBody UpdateMerchantRequest request
    ) throws DataNotFoundException {
        return merchantService.updateMerchant(merchantId, request);
    }

    @DeleteMapping("/{merchantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMerchant(@PathVariable @Min(1) String merchantId) throws DataNotFoundException {
        merchantService.deleteMerchant(merchantId);
    }

    @GetMapping("/{merchantId}/addresses")
    public MerchantAddressListResponse listMerchantAddresses(@PathVariable @Min(1) String merchantId)
        throws DataNotFoundException {
        return merchantService.listMerchantAddresses(merchantId);
    }

    @PostMapping("/{merchantId}/addresses")
    public ResponseEntity<MerchantAddressResponse> createMerchantAddress(
        @PathVariable @Min(1) String merchantId,
        @Valid @RequestBody CreateMerchantAddressRequest request
    ) throws DataNotFoundException, InvalidRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(merchantService.createMerchantAddress(merchantId, request));
    }

    @PutMapping("/{merchantId}/addresses/{addressId}")
    public MerchantAddressResponse updateMerchantAddress(
        @PathVariable @Min(1) String merchantId,
        @PathVariable @Min(1) Long addressId,
        @Valid @RequestBody UpdateMerchantAddressRequest request
    ) throws DataNotFoundException, InvalidRequestException {
        return merchantService.updateMerchantAddress(merchantId, addressId, request);
    }

    @DeleteMapping("/{merchantId}/addresses/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMerchantAddress(
        @PathVariable @Min(1) String merchantId,
        @PathVariable @Min(1) Long addressId
    ) throws DataNotFoundException {
        merchantService.deleteMerchantAddress(merchantId, addressId);
    }
}
