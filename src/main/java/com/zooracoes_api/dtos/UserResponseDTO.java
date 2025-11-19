package com.zooracoes_api.dtos;

import com.zooracoes_api.entities.Role;
import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        Role role,
        boolean active,
        LocalDateTime createdAt
) {
}

