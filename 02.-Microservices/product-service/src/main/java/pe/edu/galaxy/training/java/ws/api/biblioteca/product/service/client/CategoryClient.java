package pe.edu.galaxy.training.java.ws.api.biblioteca.product.service.client;

import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.CategoryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CategoryClient {
    Mono<CategoryResponse> findById(Long id);
    Flux<CategoryResponse> findByIds(List<Long> ids);
}
