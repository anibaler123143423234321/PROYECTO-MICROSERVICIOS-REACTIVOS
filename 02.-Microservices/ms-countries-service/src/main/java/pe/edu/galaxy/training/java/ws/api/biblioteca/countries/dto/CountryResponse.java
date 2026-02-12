package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto;


import lombok.Builder;

@Builder
public record CountryResponse(
        Long id,
        String code,
        String name
) {
}