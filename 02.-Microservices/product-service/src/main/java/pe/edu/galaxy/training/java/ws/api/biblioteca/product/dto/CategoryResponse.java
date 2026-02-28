package pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Long id,
        String name,
        String description
) {}
