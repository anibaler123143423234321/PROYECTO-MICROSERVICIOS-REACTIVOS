package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto;

import jakarta.validation.constraints.*;

public record StockDetailRequest(

    @NotBlank(message = "SKU is required")
    String sku,

    @NotBlank(message = "Location is required")
    String location

) {}
