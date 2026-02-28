package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.client;

import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface ProductClient {
    Mono<ProductResponse> findById(Long id);
    Flux<ProductResponse> findByIds(Collection<Long> ids);
}
