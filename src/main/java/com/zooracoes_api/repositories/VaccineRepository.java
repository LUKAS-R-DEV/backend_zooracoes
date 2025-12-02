package com.zooracoes_api.repositories;

import com.zooracoes_api.entities.VaccineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaccineRepository extends JpaRepository<VaccineEntity, Long> {
    List<VaccineEntity> findByPetId(Long petId);
    List<VaccineEntity> findByPetIdAndActiveTrue(Long petId);
    org.springframework.data.domain.Page<VaccineEntity> findByActiveTrue(org.springframework.data.domain.Pageable pageable);
}
