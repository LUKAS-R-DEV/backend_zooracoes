package com.zooracoes_api.dtos;

import java.time.LocalDate;

public record VaccineResponseDTO(
        Long id,
        Long petId,
        String petName,
        String vaccineName,
        LocalDate appliedDate,
        LocalDate nextDoseDate,
        String notes
) {}
