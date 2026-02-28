package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto;

import jakarta.validation.constraints.*;

public record StockRequest(
	
    @NotNull(message = "Product id is required")
    @Positive
    Long productId,

    @NotNull(message = "Quantity is required")
    @PositiveOrZero
    Integer quantity,

    @NotBlank(message = "Location is required")
    String location,

    @NotNull(message = "Provider id is required")
    @Positive
    Long providerId

) {}
