package com.zooracoes_api.repositories;

import com.zooracoes_api.entities.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long> {
    List<PrescriptionEntity> findByPetId(Long petId);
}
