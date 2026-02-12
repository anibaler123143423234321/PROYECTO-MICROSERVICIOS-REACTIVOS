package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.mapper;

import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.InventoryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.InventoryResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.ProviderResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.entity.InventoryEntity;

@Component
public class InventoryMapperImpl implements InventoryMapper {

    @Override
    public InventoryEntity toEntity(InventoryRequest inventoryRequest) {
        return InventoryEntity.builder()
                .name(inventoryRequest.name())
                .description(inventoryRequest.description())
                .sku(inventoryRequest.sku())
                .location(inventoryRequest.location())
                .providerId(inventoryRequest.providerId())
                .build();
    }

    @Override
    public InventoryResponse toDto(InventoryEntity inventoryEntity, ProviderResponse providerResponse) {
        return InventoryResponse.builder()
                .id(inventoryEntity.getId())
                .name(inventoryEntity.getName())
                .description(inventoryEntity.getDescription())
                .sku(inventoryEntity.getSku())
                .location(inventoryEntity.getLocation())
                .provider(providerResponse)
                .build();
    }

    @Override
    public InventoryResponse toDto(InventoryEntity inventoryEntity) {
        return InventoryResponse.builder()
                .id(inventoryEntity.getId())
                .name(inventoryEntity.getName())
                .description(inventoryEntity.getDescription())
                .sku(inventoryEntity.getSku())
                .location(inventoryEntity.getLocation())
                .build();
    }
}
