package pe.edu.galaxy.training.java.ws.api.biblioteca.category.mapper;

import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto.CategoryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto.CategoryResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.entity.CategoryEntity;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryEntity toEntity(CategoryRequest request) {
        if (request == null) return null;
        return CategoryEntity.builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    @Override
    public CategoryResponse toDto(CategoryEntity entity) {
        if (entity == null) return null;
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
