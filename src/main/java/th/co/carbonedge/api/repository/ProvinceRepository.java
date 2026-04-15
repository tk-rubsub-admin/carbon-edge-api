package th.co.carbonedge.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import th.co.carbonedge.api.domain.Province;

public interface ProvinceRepository extends JpaRepository<Province, String> {
}
