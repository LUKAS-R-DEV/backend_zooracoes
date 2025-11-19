package com.zooracoes_api.dtos;

import com.zooracoes_api.entities.ScheduleStatus;

import java.time.LocalDateTime;

public record ScheduleResponseDTO(
        Long id,
        Long tutorId,
        Long petId,
        Long serviceId,
        LocalDateTime dateTime,
        String notes,
        ScheduleStatus status
) {}
