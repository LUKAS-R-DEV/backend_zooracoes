package com.zooracoes_api.repositories;

import com.zooracoes_api.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    java.util.List<ServiceEntity> findByActiveTrue();
    org.springframework.data.domain.Page<ServiceEntity> findByActiveTrue(org.springframework.data.domain.Pageable pageable);
}
