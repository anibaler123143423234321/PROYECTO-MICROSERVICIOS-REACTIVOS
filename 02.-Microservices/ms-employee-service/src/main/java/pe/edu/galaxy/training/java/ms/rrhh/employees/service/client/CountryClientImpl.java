package pe.edu.galaxy.training.java.ms.rrhh.employees.service.client;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import pe.edu.galaxy.training.java.ms.rrhh.commons.errors.client.CountriesServiceErrors;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.CountryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.concurrent.TimeoutException;

@Component
@RequiredArgsConstructor
public class CountryClientImpl implements  CountryClient{

    private final WebClient webClient;

    @Override

    @CircuitBreaker(name = "countriesService", fallbackMethod = "fallbackFindById")
    @Retry(name = "countriesService")
    @TimeLimiter(name = "countriesService")
    public Mono<CountryResponse> findById(Long id) {
        return webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        response -> Mono.error(
                                CountriesServiceErrors.serviceUnavailable()
                        )
                )
                .onStatus(
                        status -> status == HttpStatus.NOT_FOUND,
                        response -> Mono.error(
                                CountriesServiceErrors.countryNotFound(id)
                        )
                )

                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> Mono.error(
                                CountriesServiceErrors.clientError()
                        )
                )
                .bodyToMono(CountryResponse.class)

                .onErrorMap(
                        WebClientRequestException.class,
                        ex -> CountriesServiceErrors.serviceUnavailable()
                )
                .onErrorMap(
                        TimeoutException.class,
                        ex -> CountriesServiceErrors.timeout()
                )
                .onErrorMap(
                        CallNotPermittedException.class,
                        ex -> CountriesServiceErrors.serviceUnavailable()
                );
                //.toFuture();
                //.subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<CountryResponse> findByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/ids");
                    ids.forEach(id -> uriBuilder.queryParam("ids", id));
                    return uriBuilder.build();
                })
                .retrieve()
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        response -> Mono.error(
                                CountriesServiceErrors.serviceUnavailable()
                        )
                )
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> Mono.error(
                                CountriesServiceErrors.clientError()
                        )
                )
                .bodyToFlux(CountryResponse.class);
    }

    /*
    private CompletableFuture<CountryResponse> fallbackFindById(Long id, Throwable ex) {
        return CompletableFuture.failedFuture(CountriesServiceErrors.serviceUnavailable());
    }*/
    private Mono<CountryResponse> fallbackFindById(Long id, Throwable ex) {
        //return Mono.just(new CountryResponse(id,"Country Error",""));
        return Mono.error(CountriesServiceErrors.serviceUnavailable());
    }
}
