package com.zooracoes_api.dtos;

import java.util.Map;

public record AnalyticsResponseDTO(
        long totalTutors,
        long totalPets,
        long totalServices,
        long totalSchedules,
        long schedulesToday,
        long vaccinesLate,
        long vaccinesNext7Days,
        Map<String, Long> petsBySpecies,
        Map<String, Long> schedulesByService
) {}
