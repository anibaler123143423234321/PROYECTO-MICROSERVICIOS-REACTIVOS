package pe.edu.galaxy.training.java.ws.api.biblioteca.product.mapper;

import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.ProductRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.ProductResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.entity.ProductEntity;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public ProductEntity toEntity(ProductRequest request) {
        return ProductEntity
                .builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    @Override
    public ProductResponse toDto(ProductEntity productEntity) {
        return ProductResponse
                .builder()
                .id(productEntity.getId())
                .name(productEntity.getName().toUpperCase())
                .description(productEntity.getDescription())
                .build();
    }

}
