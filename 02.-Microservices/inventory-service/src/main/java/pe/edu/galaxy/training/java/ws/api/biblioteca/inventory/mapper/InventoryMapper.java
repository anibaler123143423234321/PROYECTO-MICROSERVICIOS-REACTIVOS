package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.mapper;

import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.InventoryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.InventoryResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.ProviderResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.entity.InventoryEntity;

public interface InventoryMapper {

    InventoryEntity toEntity(InventoryRequest inventoryRequest);

    InventoryResponse toDto(InventoryEntity inventoryEntity, ProviderResponse providerResponse);

    InventoryResponse toDto(InventoryEntity inventoryEntity);
}
