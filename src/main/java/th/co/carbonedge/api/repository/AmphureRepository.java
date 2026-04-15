package th.co.carbonedge.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import th.co.carbonedge.api.domain.Amphure;

public interface AmphureRepository extends JpaRepository<Amphure, String> {

    List<Amphure> findAllByThaiProvinceCodeOrderByCodeAsc(String thaiProvinceCode);
}
