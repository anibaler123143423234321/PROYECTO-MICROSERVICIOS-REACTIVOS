package pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductRequest(
	
	@NotBlank(message = "The name is required")
    @Size(min = 2, max = 60, message = "The nave must have {min} or {max} characters")
    String name,

    @NotBlank(message = "The description is required")
    @Size(min = 5, max = 400, message = "The description must have {min} or {max} characters")
    String description
) {}
