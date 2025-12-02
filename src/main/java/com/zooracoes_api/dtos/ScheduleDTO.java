package com.zooracoes_api.dtos;

import java.time.LocalDateTime;

public record ScheduleDTO(
        Long petId,
        Long serviceId,
        LocalDateTime dateTime,
        String notes,
        String petName,
        String serviceName,
        String tutorName
) {}
