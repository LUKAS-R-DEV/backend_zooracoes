package com.zooracoes_api.repositories;

import com.zooracoes_api.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndActiveTrue(String email);
    java.util.List<UserEntity> findByActiveTrue();
    org.springframework.data.domain.Page<UserEntity> findByActiveTrue(org.springframework.data.domain.Pageable pageable);
}


