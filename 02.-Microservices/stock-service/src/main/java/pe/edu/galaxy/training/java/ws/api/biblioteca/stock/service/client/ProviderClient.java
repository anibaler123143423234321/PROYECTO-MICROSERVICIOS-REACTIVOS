package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.client;

import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProviderResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface ProviderClient {

    Mono<ProviderResponse> findById(Long id);

    Flux<ProviderResponse> findByIds(Collection<Long> ids);
}
