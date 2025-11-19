package com.zooracoes_api.dtos;

import java.time.LocalDateTime;

public record PrescriptionResponseDTO(
        Long id,
        Long petId,
        String medicationName,
        String dosage,
        String instructions,
        LocalDateTime prescribedAt
) {}
