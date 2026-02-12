package pe.edu.galaxy.training.java.ws.api.biblioteca.product.mapper;

import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.ProductRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.ProductResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.entity.ProductEntity;

public interface ProductMapper {

    ProductEntity toEntity(ProductRequest request);

    ProductResponse toDto(ProductEntity productEntity);

}
