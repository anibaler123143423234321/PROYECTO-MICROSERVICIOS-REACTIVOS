package pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must have {min} characters and must not exceed {max} characters")
    String name,

    @NotBlank(message = "Description is required")
    @Size(max = 100)
    String description
) {
}