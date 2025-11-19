package com.zooracoes_api.dtos;

import com.zooracoes_api.entities.Role;

public record RegisterDTO(String name, String email, String password, Role role) {}
