package pe.edu.galaxy.training.java.ms.rrhh.employees.service.client;

import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.CountryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface CountryClient {

    //CompletableFuture<CountryResponse> findById(Long id);

    Mono<CountryResponse> findById(Long id);

    Flux<CountryResponse> findByIds(Collection<Long> ids);
}
