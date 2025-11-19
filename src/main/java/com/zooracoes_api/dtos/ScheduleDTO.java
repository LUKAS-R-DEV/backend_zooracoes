package com.zooracoes_api.dtos;

import java.time.LocalDateTime;

public record ScheduleDTO(
        Long tutorId,
        Long petId,
        Long serviceId,
        LocalDateTime dateTime,
        String notes
) {}
