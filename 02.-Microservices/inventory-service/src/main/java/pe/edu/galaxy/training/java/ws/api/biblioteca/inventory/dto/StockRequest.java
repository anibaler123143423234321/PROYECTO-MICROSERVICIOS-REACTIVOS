package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto;

import jakarta.validation.constraints.*;

public record StockRequest(

    @NotBlank(message = "SKU is required")
    String sku,

    @NotBlank(message = "Location is required")
    String location

) {}
