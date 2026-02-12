package pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
    Long id,
    String name,
    String description
) {
}