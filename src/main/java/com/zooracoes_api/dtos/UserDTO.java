package com.zooracoes_api.dtos;

import com.zooracoes_api.entities.Role;

public record UserDTO(String name, String email, String password, Role role) {}


