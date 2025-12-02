package com.zooracoes_api.dtos;

import java.time.LocalDate;

public record PetDTO(
        String name,
        String species,
        String breed,
        Double weight,
        LocalDate birthDate,
        Long tutorId,
        String tutorName
) {}
