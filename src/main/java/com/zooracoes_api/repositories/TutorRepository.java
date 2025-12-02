package com.zooracoes_api.repositories;

import com.zooracoes_api.entities.TutorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<TutorEntity, Long> {
    Page<TutorEntity> findAll(Pageable pageable);
    Page<TutorEntity> findByActiveTrue(Pageable pageable);
    Page<TutorEntity> findByActiveFalse(Pageable pageable);
}
