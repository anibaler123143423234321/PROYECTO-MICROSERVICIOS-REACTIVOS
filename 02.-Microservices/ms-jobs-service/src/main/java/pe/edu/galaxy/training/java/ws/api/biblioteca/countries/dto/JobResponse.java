package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto;


import lombok.Builder;

@Builder
public record JobResponse(
        Long id,
        String name,
        String description
) {
}