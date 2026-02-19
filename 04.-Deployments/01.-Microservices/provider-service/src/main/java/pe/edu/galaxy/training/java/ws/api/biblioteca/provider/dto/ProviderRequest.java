package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto;

import jakarta.validation.constraints.*;

public record ProviderRequest(
    
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must have {min} characters and must not exceed {max} characters")
    String name,

    @NotBlank(message = "Description is required")
    @Size(max = 100)
    String description,

    @NotBlank(message = "Phone is required")
    String phone,

    @NotBlank(message = "Email is required")
    @Email
    String email
) {}
