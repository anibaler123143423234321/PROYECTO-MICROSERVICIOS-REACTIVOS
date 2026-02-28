package pe.edu.galaxy.training.java.ws.api.biblioteca.category.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto.CategoryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto.CategoryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

public interface CategoryService {

    Flux<CategoryResponse> findAll();

    Flux<CategoryResponse> findByName(String name);

    Flux<CategoryResponse> findByIds(List<Long> ids);

    Mono<CategoryResponse> findById(Long id);

    Mono<Void> save(CategoryRequest request);

    Mono<Void> update(Long id, CategoryRequest request);

    Mono<Void> delete(Long id);
}
