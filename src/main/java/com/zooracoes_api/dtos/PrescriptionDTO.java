package com.zooracoes_api.dtos;

public record PrescriptionDTO(
        Long petId,
        String medicationName,
        String dosage,
        String instructions
) {}
