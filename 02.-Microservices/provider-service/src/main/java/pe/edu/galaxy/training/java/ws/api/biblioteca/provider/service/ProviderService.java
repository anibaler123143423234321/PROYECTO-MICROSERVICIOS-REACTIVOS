package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto.ProviderRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto.ProviderResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Set;

public interface ProviderService {

    Flux<ProviderResponse> findAll();

    Flux<ProviderResponse> findByName(String name);

    Mono<ProviderResponse> findById(Long id);

    Flux<ProviderResponse> findByIds(Set<Long> ids);

    Mono<Void> save(ProviderRequest providerRequest);

    Mono<Void> update(Long id, ProviderRequest providerRequest);

    Mono<Void> delete(Long id);
}
