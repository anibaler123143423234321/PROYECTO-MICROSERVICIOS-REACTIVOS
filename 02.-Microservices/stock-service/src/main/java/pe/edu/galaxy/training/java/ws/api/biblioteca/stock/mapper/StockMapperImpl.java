package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.mapper;

import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProviderResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProductResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.entity.StockEntity;

@Component
public class StockMapperImpl implements StockMapper {

    @Override
    public StockEntity toEntity(StockRequest stockRequest) {
        return StockEntity.builder()
                .productId(stockRequest.productId())
                .quantity(stockRequest.quantity())
                .location(stockRequest.location())
                .providerId(stockRequest.providerId())
                .build();
    }

    @Override
    public StockResponse toDto(StockEntity stockEntity, ProductResponse productResponse, ProviderResponse providerResponse) {
        return StockResponse.builder()
                .id(stockEntity.getId())
                .product(productResponse)
                .quantity(stockEntity.getQuantity())
                .location(stockEntity.getLocation())
                .provider(providerResponse)
                .build();
    }

    @Override
    public StockResponse toDto(StockEntity stockEntity) {
        return StockResponse.builder()
                .id(stockEntity.getId())
                .quantity(stockEntity.getQuantity())
                .location(stockEntity.getLocation())
                .build();
    }
}
