package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CountryRequest (
	
	@NotBlank(message = "CountryEntity code is required")
    @Size(min = 2, max = 3, message = "CountryEntity code must have 2 or 3 characters")
    String code,

    @NotBlank(message = "CountryEntity name is required")
    @Size(max = 100, message = "CountryEntity name must not exceed 100 characters")
    String name
) {}