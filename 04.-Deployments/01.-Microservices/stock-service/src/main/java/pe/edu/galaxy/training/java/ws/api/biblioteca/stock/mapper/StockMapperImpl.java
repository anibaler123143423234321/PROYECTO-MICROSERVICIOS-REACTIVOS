package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.mapper;

import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProviderResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.entity.StockEntity;

@Component
public class StockMapperImpl implements StockMapper {

    @Override
    public StockEntity toEntity(StockRequest stockRequest) {
        return StockEntity.builder()
                .name(stockRequest.name())
                .description(stockRequest.description())
                .sku(stockRequest.sku())
                .location(stockRequest.location())
                .providerId(stockRequest.providerId())
                .build();
    }

    @Override
    public StockResponse toDto(StockEntity stockEntity, ProviderResponse providerResponse) {
        return StockResponse.builder()
                .id(stockEntity.getId())
                .name(stockEntity.getName())
                .description(stockEntity.getDescription())
                .sku(stockEntity.getSku())
                .location(stockEntity.getLocation())
                .provider(providerResponse)
                .build();
    }

    @Override
    public StockResponse toDto(StockEntity stockEntity) {
        return StockResponse.builder()
                .id(stockEntity.getId())
                .name(stockEntity.getName())
                .description(stockEntity.getDescription())
                .sku(stockEntity.getSku())
                .location(stockEntity.getLocation())
                .build();
    }
}
