package pe.edu.galaxy.training.java.ws.api.biblioteca.category.mapper;

import pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto.CategoryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto.CategoryResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.entity.CategoryEntity;

public interface CategoryMapper {

    CategoryEntity toEntity(CategoryRequest request);

    CategoryResponse toDto(CategoryEntity entity);
}
