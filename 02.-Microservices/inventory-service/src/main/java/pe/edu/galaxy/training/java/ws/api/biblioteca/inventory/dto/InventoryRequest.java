package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto;

import jakarta.validation.constraints.*;

public record InventoryRequest(
	
	@NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must have {min} characters and must not exceed {max} characters")
    String name,

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 100, message = "Description must have {min} characters and must not exceed {max} characters")
    String description,

    @NotBlank(message = "SKU is required")
    String sku,

    @NotBlank(message = "Location is required")
    String location,

    @NotNull(message = "Provider id is required")
    @Positive
    Long providerId

) {}
