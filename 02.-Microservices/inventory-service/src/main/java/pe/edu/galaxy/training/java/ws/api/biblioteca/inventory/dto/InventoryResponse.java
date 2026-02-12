package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto;

import lombok.Builder;

@Builder
public record InventoryResponse(
        Long id,
        String name,
        String description,
        String sku,
        String location,
        ProviderResponse provider
) {
}
