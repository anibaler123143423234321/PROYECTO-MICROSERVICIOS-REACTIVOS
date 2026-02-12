package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto;

import lombok.Builder;

@Builder
public record ProviderResponse(
    Long id,
    String name,
    String description,
    String phone,
    String email
) {}
