package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto;

import lombok.Builder;

@Builder
public record StockResponse(
        Long id,
        ProductResponse product,
        Integer quantity,
        String location,
        ProviderResponse provider
) {
}
