package com.zooracoes_api.repositories;

import com.zooracoes_api.entities.PetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<PetEntity, Long> {
    List<PetEntity> findByTutor_Id(Long tutorId);
    List<PetEntity> findByTutor_IdAndActiveTrue(Long tutorId);
    
    Page<PetEntity> findBySpecies(String species, Pageable pageable);
    Page<PetEntity> findBySpeciesAndActiveTrue(String species, Pageable pageable);
    
    Page<PetEntity> findByTutor_Id(Long tutorId, Pageable pageable);
    Page<PetEntity> findByTutor_IdAndActiveTrue(Long tutorId, Pageable pageable);
    
    Page<PetEntity> findByActiveTrue(Pageable pageable);
    
    @Query("SELECT p FROM PetEntity p WHERE p.active = true AND " +
           "(:species IS NULL OR p.species = :species) AND " +
           "(:tutorId IS NULL OR p.tutor.id = :tutorId)")
    Page<PetEntity> findByFilters(@Param("species") String species, 
                                  @Param("tutorId") Long tutorId, 
                                  Pageable pageable);
}
