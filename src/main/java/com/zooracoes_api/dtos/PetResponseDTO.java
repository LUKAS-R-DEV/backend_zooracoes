package com.zooracoes_api.dtos;

import java.time.LocalDate;

public record PetResponseDTO(
        Long id,
        String name,
        String species,
        String breed,
        Double weight,
        LocalDate birthDate,
        Long tutorId,
        String tutorName
) {}
