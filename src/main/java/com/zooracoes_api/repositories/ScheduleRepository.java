package com.zooracoes_api.repositories;

import com.zooracoes_api.entities.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT s FROM ScheduleEntity s WHERE " +
           "(:date IS NULL OR CAST(s.dateTime AS date) = :date) AND " +
           "(:serviceId IS NULL OR s.service.id = :serviceId)")
    Page<ScheduleEntity> findByFilters(@Param("date") LocalDate date,
                                       @Param("serviceId") Long serviceId,
                                       Pageable pageable);
}
