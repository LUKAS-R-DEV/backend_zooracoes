package com.zooracoes_api.dtos;

public record ServiceResponseDTO(Long id, String name, String description, boolean active, double price, Integer duration) {}
