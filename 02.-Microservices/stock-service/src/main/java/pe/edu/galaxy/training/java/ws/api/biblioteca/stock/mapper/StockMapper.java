package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.mapper;

import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProviderResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.entity.StockEntity;

public interface StockMapper {

    StockEntity toEntity(StockRequest stockRequest);

    StockResponse toDto(StockEntity stockEntity, pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProductResponse productResponse, ProviderResponse providerResponse);

    StockResponse toDto(StockEntity stockEntity);
}
