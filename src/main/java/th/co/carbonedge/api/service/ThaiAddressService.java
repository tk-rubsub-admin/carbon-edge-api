package th.co.carbonedge.api.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import th.co.carbonedge.api.dto.address.ThaiAmphureListResponse;
import th.co.carbonedge.api.dto.address.ThaiProvinceListResponse;
import th.co.carbonedge.api.dto.address.ThaiTumbonListResponse;
import th.co.carbonedge.api.repository.AmphureRepository;
import th.co.carbonedge.api.repository.ProvinceRepository;
import th.co.carbonedge.api.repository.TumbonRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ThaiAddressService {

    private final ProvinceRepository provinceRepository;
    private final AmphureRepository amphureRepository;
    private final TumbonRepository tumbonRepository;

    public ThaiProvinceListResponse listProvinces() {
        return new ThaiProvinceListResponse().setData(
            provinceRepository.findAll(Sort.by(Sort.Order.asc("code"))).stream()
                .map(province -> new th.co.carbonedge.api.dto.address.ThaiProvince()
                    .setCode(province.getCode())
                    .setNameTh(province.getNameTh())
                    .setNameEn(province.getNameEn()))
                .toList()
        );
    }

    public ThaiAmphureListResponse listAmphures(String thaiProvinceCode) {
        return new ThaiAmphureListResponse().setData(
            amphureRepository.findAllByThaiProvinceCodeOrderByCodeAsc(thaiProvinceCode).stream()
                .map(amphure -> new th.co.carbonedge.api.dto.address.ThaiAmphure()
                    .setCode(amphure.getCode())
                    .setThaiProvinceCode(amphure.getThaiProvinceCode())
                    .setNameTh(amphure.getNameTh())
                    .setNameEn(amphure.getNameEn()))
                .toList()
        );
    }

    public ThaiTumbonListResponse listTumbons(String thaiAmphureCode) {
        return new ThaiTumbonListResponse().setData(
            tumbonRepository.findAllByThaiAmphureCodeOrderByCodeAsc(thaiAmphureCode).stream()
                .map(tumbon -> new th.co.carbonedge.api.dto.address.ThaiTumbon()
                    .setCode(tumbon.getCode())
                    .setThaiAmphureCode(tumbon.getThaiAmphureCode())
                    .setNameTh(tumbon.getNameTh())
                    .setNameEn(tumbon.getNameEn())
                    .setPostalCode(tumbon.getPostalCode()))
                .toList()
        );
    }
}
