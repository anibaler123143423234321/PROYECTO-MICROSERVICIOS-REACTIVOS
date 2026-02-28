package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto;

import lombok.Builder;

@Builder
public record ProductResponse(
        Long id,
        String name,
        String description,
        CategoryResponse category
) {}
