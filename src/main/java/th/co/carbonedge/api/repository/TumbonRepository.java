package th.co.carbonedge.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import th.co.carbonedge.api.domain.Tumbon;

public interface TumbonRepository extends JpaRepository<Tumbon, String> {

    List<Tumbon> findAllByThaiAmphureCodeOrderByCodeAsc(String thaiAmphureCode);
}
