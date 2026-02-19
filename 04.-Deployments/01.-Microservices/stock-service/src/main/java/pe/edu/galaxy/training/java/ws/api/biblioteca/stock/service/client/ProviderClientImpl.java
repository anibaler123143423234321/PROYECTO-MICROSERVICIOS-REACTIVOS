package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.client;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.client.ProviderServiceErrors;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProviderResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class ProviderClientImpl implements ProviderClient {

    private final WebClient webClient;

    @Override
    @CircuitBreaker(name = "providerService", fallbackMethod = "fallbackFindById")
    @Retry(name = "providerService")
    @TimeLimiter(name = "providerService")
    public Mono<ProviderResponse> findById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        response -> Mono.error(
                                ProviderServiceErrors.serviceUnavailable()
                        )
                )
                .onStatus(
                        status -> status == HttpStatus.NOT_FOUND,
                        response -> Mono.error(
                                ProviderServiceErrors.providerNotFound(id)
                        )
                )
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> Mono.error(
                                ProviderServiceErrors.clientError()
                        )
                )
                .bodyToMono(ProviderResponse.class)
                .onErrorMap(
                        WebClientRequestException.class,
                        ex -> ProviderServiceErrors.serviceUnavailable()
                )
                .onErrorMap(
                        TimeoutException.class,
                        ex -> ProviderServiceErrors.timeout()
                )
                .onErrorMap(
                        CallNotPermittedException.class,
                        ex -> ProviderServiceErrors.serviceUnavailable()
                );
    }

    @Override
    public Flux<ProviderResponse> findByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/by-ids");
                    ids.forEach(id -> uriBuilder.queryParam("ids", id));
                    return uriBuilder.build();
                })
                .retrieve()
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        response -> Mono.error(
                                ProviderServiceErrors.serviceUnavailable()
                        )
                )
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> Mono.error(
                                ProviderServiceErrors.clientError()
                        )
                )
                .bodyToFlux(ProviderResponse.class);
    }

    private Mono<ProviderResponse> fallbackFindById(Long id, Throwable ex) {
        return Mono.error(ProviderServiceErrors.serviceUnavailable());
    }
}
