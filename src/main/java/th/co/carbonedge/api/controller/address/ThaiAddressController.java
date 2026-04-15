package th.co.carbonedge.api.controller.address;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import th.co.carbonedge.api.dto.address.ThaiAmphureListResponse;
import th.co.carbonedge.api.dto.address.ThaiProvinceListResponse;
import th.co.carbonedge.api.dto.address.ThaiTumbonListResponse;
import th.co.carbonedge.api.service.ThaiAddressService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/address")
@Tag(name = "Thai Address", description = "Thai address master data APIs")
public class ThaiAddressController {

    private final ThaiAddressService thaiAddressService;

    @GetMapping("/provinces")
    public ThaiProvinceListResponse listProvinces() {
        return thaiAddressService.listProvinces();
    }

    @GetMapping("/amphures")
    public ThaiAmphureListResponse listAmphures(@RequestParam @NotBlank String provinceCode) {
        return thaiAddressService.listAmphures(provinceCode);
    }

    @GetMapping("/tumbons")
    public ThaiTumbonListResponse listTumbons(@RequestParam @NotBlank String amphureCode) {
        return thaiAddressService.listTumbons(amphureCode);
    }
}
