package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto;

import jakarta.validation.constraints.*;

public record StockDetailRequest(

    @NotNull(message = "Quantity is required")
    @PositiveOrZero
    Integer quantity,

    @NotBlank(message = "Location is required")
    String location

) {}
