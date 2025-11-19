package com.zooracoes_api.dtos;

import java.util.List;

public record PageResponseDTO<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static <T> PageResponseDTO<T> of(List<T> content, int page, int size, long totalElements, int totalPages) {
        return new PageResponseDTO<>(content, page, size, totalElements, totalPages);
    }
}



