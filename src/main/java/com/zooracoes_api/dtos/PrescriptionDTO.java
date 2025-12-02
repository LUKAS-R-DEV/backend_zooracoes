package com.zooracoes_api.dtos;

import java.time.LocalDate;

public record PrescriptionDTO(
        Long petId,
        String medicationName,
        String dosage,
        String instructions,
        LocalDate startDate,
        LocalDate endDate,
        String veterinarian
) {}
