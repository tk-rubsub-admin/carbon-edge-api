package th.co.carbonedge.api.service;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Comparator;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import th.co.carbonedge.api.constant.MerchantBusinessType;
import th.co.carbonedge.api.constant.MerchantStatus;
import th.co.carbonedge.api.domain.Merchant;
import th.co.carbonedge.api.domain.MerchantAddress;
import th.co.carbonedge.api.dto.merchant.CreateMerchantAddressRequest;
import th.co.carbonedge.api.dto.merchant.CreateMerchantRequest;
import th.co.carbonedge.api.dto.merchant.MerchantAddressListResponse;
import th.co.carbonedge.api.dto.merchant.MerchantAddressResponse;
import th.co.carbonedge.api.dto.merchant.MerchantPageResponse;
import th.co.carbonedge.api.dto.merchant.MerchantResponse;
import th.co.carbonedge.api.dto.merchant.UpdateMerchantAddressRequest;
import th.co.carbonedge.api.dto.merchant.UpdateMerchantRequest;
import th.co.carbonedge.api.exception.DataConflictException;
import th.co.carbonedge.api.exception.DataNotFoundException;
import th.co.carbonedge.api.exception.InvalidRequestException;
import th.co.carbonedge.api.repository.AmphureRepository;
import th.co.carbonedge.api.repository.MerchantRepository;
import th.co.carbonedge.api.repository.ProvinceRepository;
import th.co.carbonedge.api.repository.TumbonRepository;
import th.co.carbonedge.api.specification.MerchantSpecification;

@Service
@Transactional
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final ProvinceRepository provinceRepository;
    private final AmphureRepository amphureRepository;
    private final TumbonRepository tumbonRepository;

    @Transactional(readOnly = true)
    public MerchantPageResponse listMerchants(
        Integer page,
        Integer size,
        String keyword,
        MerchantStatus status,
        MerchantBusinessType businessType,
        Boolean isOfficial
    ) {
        Pageable pageable = PageRequest.of(
            page == null ? 0 : page,
            size == null ? 20 : size,
            Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Merchant> result = merchantRepository.findAll(
            MerchantSpecification.withFilters(keyword, status, businessType, isOfficial),
            pageable
        );

        return new MerchantPageResponse()
            .setData(result.getContent().stream().map(this::toDto).toList())
            .setPage(result.getNumber())
            .setSize(result.getSize())
            .setTotalElements(result.getTotalElements())
            .setTotalPages(result.getTotalPages())
            .setHasNext(result.hasNext());
    }

    @Transactional(readOnly = true)
    public MerchantResponse getMerchantById(String merchantId) throws DataNotFoundException {
        return new MerchantResponse().setData(toDto(findActiveMerchant(merchantId)));
    }

    public MerchantResponse createMerchant(CreateMerchantRequest request) {
        validateUniqueness(request.getMerchantCode(), request.getShopSlug(), request.getEmail(), request.getTaxId(), null);
        Merchant merchant = new Merchant();
        applyCreateRequest(merchant, request);
        return new MerchantResponse().setData(toDto(merchantRepository.save(merchant)));
    }

    public MerchantResponse updateMerchant(String merchantId, UpdateMerchantRequest request) throws DataNotFoundException {
        Merchant merchant = findActiveMerchant(merchantId);
        validateUniqueness(request.getMerchantCode(), request.getShopSlug(), request.getEmail(), request.getTaxId(), merchantId);
        applyUpdateRequest(merchant, request);
        return new MerchantResponse().setData(toDto(merchantRepository.save(merchant)));
    }

    public void deleteMerchant(String merchantId) throws DataNotFoundException {
        Merchant merchant = findActiveMerchant(merchantId);
        merchant.setStatus(MerchantStatus.INACTIVE);
        merchantRepository.save(merchant);
    }

    @Transactional(readOnly = true)
    public MerchantAddressListResponse listMerchantAddresses(String merchantId) throws DataNotFoundException {
        Merchant merchant = findActiveMerchant(merchantId);
        List<th.co.carbonedge.api.dto.merchant.MerchantAddress> addresses = merchant.getAddresses().stream()
            .map(address -> toAddressDto(merchant.getId(), address))
            .toList();
        return new MerchantAddressListResponse().setData(addresses);
    }

    public MerchantAddressResponse createMerchantAddress(String merchantId, CreateMerchantAddressRequest request)
        throws DataNotFoundException, InvalidRequestException {
        Merchant merchant = findActiveMerchant(merchantId);
        validateThaiAddressCodes(
            request.getThaiProvinceCode(),
            request.getThaiAmphureCode(),
            request.getThaiTumbonCode()
        );
        if (Boolean.TRUE.equals(request.getIsDefault())) {
            clearDefaultAddress(merchant);
        }

        MerchantAddress address = MerchantAddress.builder()
            .id(nextAddressId(merchant.getAddresses()))
            .addressType(request.getAddressType())
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

        merchant.getAddresses().add(address);
        Merchant savedMerchant = merchantRepository.save(merchant);
        MerchantAddress savedAddress = findAddress(savedMerchant, address.getId());
        return new MerchantAddressResponse().setData(toAddressDto(savedMerchant.getId(), savedAddress));
    }

    public MerchantAddressResponse updateMerchantAddress(
        String merchantId,
        Long addressId,
        UpdateMerchantAddressRequest request
    ) throws DataNotFoundException, InvalidRequestException {
        Merchant merchant = findActiveMerchant(merchantId);
        MerchantAddress address = findAddress(merchant, addressId);

        if (request.getAddressType() != null) {
            address.setAddressType(request.getAddressType());
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
            clearDefaultAddress(merchant);
            address.setIsDefault(Boolean.TRUE);
        } else if (request.getIsDefault() != null) {
            address.setIsDefault(request.getIsDefault());
        }

        Merchant savedMerchant = merchantRepository.save(merchant);
        MerchantAddress savedAddress = findAddress(savedMerchant, addressId);
        return new MerchantAddressResponse().setData(toAddressDto(savedMerchant.getId(), savedAddress));
    }

    public void deleteMerchantAddress(String merchantId, Long addressId) throws DataNotFoundException {
        Merchant merchant = findActiveMerchant(merchantId);
        MerchantAddress address = findAddress(merchant, addressId);
        merchant.getAddresses().remove(address);
        merchantRepository.save(merchant);
    }

    private Merchant findActiveMerchant(String merchantId) throws DataNotFoundException {
        return merchantRepository.findByIdAndStatusNot(merchantId, MerchantStatus.INACTIVE)
            .orElseThrow(() -> new DataNotFoundException("Merchant " + merchantId + " not found"));
    }

    private MerchantAddress findAddress(Merchant merchant, Long addressId) throws DataNotFoundException {
        return merchant.getAddresses().stream()
            .filter(address -> addressId.equals(address.getId()))
            .findFirst()
            .orElseThrow(() -> new DataNotFoundException("Merchant address " + addressId + " not found"));
    }

    private Long nextAddressId(List<MerchantAddress> addresses) {
        return addresses.stream()
            .map(MerchantAddress::getId)
            .filter(id -> id != null)
            .max(Comparator.naturalOrder())
            .orElse(0L) + 1;
    }

    private void clearDefaultAddress(Merchant merchant) {
        merchant.getAddresses().forEach(address -> address.setIsDefault(Boolean.FALSE));
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

    private void validateUniqueness(
        String merchantCode,
        String shopSlug,
        String email,
        String taxId,
        String currentMerchantId
    ) {
        validateDuplicate("Merchant code", merchantCode, currentMerchantId,
            id -> merchantRepository.existsByMerchantCodeAndIdNot(merchantCode, id),
            () -> merchantRepository.existsByMerchantCode(merchantCode));
        validateDuplicate("Merchant shop slug", shopSlug, currentMerchantId,
            id -> merchantRepository.existsByShopSlugAndIdNot(shopSlug, id),
            () -> merchantRepository.existsByShopSlug(shopSlug));
        validateDuplicate("Merchant email", email, currentMerchantId,
            id -> merchantRepository.existsByEmailAndIdNot(email, id),
            () -> merchantRepository.existsByEmail(email));
        validateDuplicate("Merchant tax ID", taxId, currentMerchantId,
            id -> merchantRepository.existsByTaxIdAndIdNot(taxId, id),
            () -> merchantRepository.existsByTaxId(taxId));
    }

    private void validateDuplicate(
        String fieldName,
        String value,
        String currentId,
        java.util.function.Function<String, Boolean> existsByIdNot,
        java.util.function.Supplier<Boolean> exists
    ) {
        if (value == null || value.isBlank()) {
            return;
        }
        boolean duplicated = currentId == null ? exists.get() : existsByIdNot.apply(currentId);
        if (duplicated) {
            throw new DataConflictException(fieldName + " already exists: " + value);
        }
    }

    private void applyCreateRequest(Merchant merchant, CreateMerchantRequest request) {
        merchant.setMerchantCode(request.getMerchantCode());
        merchant.setShopName(request.getShopName());
        merchant.setShopSlug(request.getShopSlug());
        merchant.setLegalName(request.getLegalName());
        merchant.setTaxId(request.getTaxId());
        merchant.setEmail(request.getEmail());
        merchant.setPhoneNumber(request.getPhoneNumber());
        merchant.setSupportEmail(request.getSupportEmail());
        merchant.setSupportPhone(request.getSupportPhone());
        merchant.setDescription(request.getDescription());
        merchant.setLogoUrl(toStringValue(request.getLogoUrl()));
        merchant.setCoverImageUrl(toStringValue(request.getCoverImageUrl()));
        merchant.setBusinessType(request.getBusinessType());
        merchant.setRegisteredCountry(request.getRegisteredCountry());
        merchant.setCurrency(request.getCurrency());
        merchant.setCommissionRate(toBigDecimal(request.getCommissionRate()));
        merchant.setIsOfficial(request.getIsOfficial());
        merchant.setStatus(request.getStatus());
    }

    private void applyUpdateRequest(Merchant merchant, UpdateMerchantRequest request) {
        if (request.getMerchantCode() != null) {
            merchant.setMerchantCode(request.getMerchantCode());
        }
        if (request.getShopName() != null) {
            merchant.setShopName(request.getShopName());
        }
        if (request.getShopSlug() != null) {
            merchant.setShopSlug(request.getShopSlug());
        }
        if (request.getLegalName() != null) {
            merchant.setLegalName(request.getLegalName());
        }
        if (request.getTaxId() != null) {
            merchant.setTaxId(request.getTaxId());
        }
        if (request.getEmail() != null) {
            merchant.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            merchant.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getSupportEmail() != null) {
            merchant.setSupportEmail(request.getSupportEmail());
        }
        if (request.getSupportPhone() != null) {
            merchant.setSupportPhone(request.getSupportPhone());
        }
        if (request.getDescription() != null) {
            merchant.setDescription(request.getDescription());
        }
        if (request.getLogoUrl() != null) {
            merchant.setLogoUrl(toStringValue(request.getLogoUrl()));
        }
        if (request.getCoverImageUrl() != null) {
            merchant.setCoverImageUrl(toStringValue(request.getCoverImageUrl()));
        }
        if (request.getBusinessType() != null) {
            merchant.setBusinessType(request.getBusinessType());
        }
        if (request.getRegisteredCountry() != null) {
            merchant.setRegisteredCountry(request.getRegisteredCountry());
        }
        if (request.getCurrency() != null) {
            merchant.setCurrency(request.getCurrency());
        }
        if (request.getCommissionRate() != null) {
            merchant.setCommissionRate(toBigDecimal(request.getCommissionRate()));
        }
        if (request.getRatingAverage() != null) {
            merchant.setRatingAverage(toBigDecimal(request.getRatingAverage()));
        }
        if (request.getIsOfficial() != null) {
            merchant.setIsOfficial(request.getIsOfficial());
        }
        if (request.getStatus() != null) {
            merchant.setStatus(request.getStatus());
        }
        if (request.getVerifiedAt() != null) {
            merchant.setVerifiedAt(request.getVerifiedAt());
        }
    }

    private th.co.carbonedge.api.dto.merchant.Merchant toDto(Merchant merchant) {
        return new th.co.carbonedge.api.dto.merchant.Merchant()
            .setId(merchant.getId())
            .setMerchantCode(merchant.getMerchantCode())
            .setShopName(merchant.getShopName())
            .setShopSlug(merchant.getShopSlug())
            .setLegalName(merchant.getLegalName())
            .setTaxId(merchant.getTaxId())
            .setEmail(merchant.getEmail())
            .setPhoneNumber(merchant.getPhoneNumber())
            .setSupportEmail(merchant.getSupportEmail())
            .setSupportPhone(merchant.getSupportPhone())
            .setDescription(merchant.getDescription())
            .setLogoUrl(toUri(merchant.getLogoUrl()))
            .setCoverImageUrl(toUri(merchant.getCoverImageUrl()))
            .setBusinessType(merchant.getBusinessType())
            .setRegisteredCountry(merchant.getRegisteredCountry())
            .setCurrency(merchant.getCurrency())
            .setCommissionRate(toDouble(merchant.getCommissionRate()))
            .setRatingAverage(toDouble(merchant.getRatingAverage()))
            .setIsOfficial(merchant.getIsOfficial())
            .setStatus(merchant.getStatus())
            .setVerifiedAt(merchant.getVerifiedAt())
            .setCreatedAt(merchant.getCreatedAt())
            .setUpdatedAt(merchant.getUpdatedAt());
    }

    private th.co.carbonedge.api.dto.merchant.MerchantAddress toAddressDto(String merchantId, MerchantAddress address) {
        return new th.co.carbonedge.api.dto.merchant.MerchantAddress()
            .setId(address.getId())
            .setMerchantId(merchantId)
            .setAddressType(address.getAddressType())
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

    private BigDecimal toBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    private Double toDouble(BigDecimal value) {
        return value == null ? null : value.doubleValue();
    }

    private String toStringValue(URI value) {
        return value == null ? null : value.toString();
    }

    private URI toUri(String value) {
        return value == null || value.isBlank() ? null : URI.create(value);
    }
}
