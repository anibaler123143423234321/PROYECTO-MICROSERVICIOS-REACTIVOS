package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto;

import lombok.Builder;

@Builder
public record StockResponse(
        Long id,
        String name,
        String description,
        String sku,
        String location,
        ProviderResponse provider
) {
}
