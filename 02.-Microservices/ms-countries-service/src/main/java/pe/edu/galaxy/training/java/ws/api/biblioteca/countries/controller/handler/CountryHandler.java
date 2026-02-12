package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.InvalidPathVariableException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.CountryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.service.CountryService;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CountryHandler {

    private final CountryService countryService;

    public Mono<ServerResponse> findById(ServerRequest request) {

        return Mono.justOrEmpty(request.pathVariable("id"))

                .flatMap(id -> {
                    try {
                        return Mono.just(Long.parseLong(id));
                    } catch (NumberFormatException ex) {
                        return Mono.error(
                                new InvalidPathVariableException(
                                        "Invalid country id: " + id
                                )
                        );
                    }
                })

                .flatMap(id -> {
                    /*
                    if (id <= 0) {
                        return Mono.error(
                                new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST,
                                        "Invalid country id"
                                )
                        );
                    }*/
                    /*
                    if (id == 5L) {
                        return Mono.error(
                                new ResponseStatusException(
                                        HttpStatus.INTERNAL_SERVER_ERROR,
                                        "Simulated internal error"
                                )
                        );
                    }*/
                    /*
                    if (id == 6L) {

                        return Mono.fromCallable(() -> {
                                    Thread.sleep(3_000);
                                    return id;
                                })
                                .subscribeOn(Schedulers.boundedElastic());
                    }*/

                    return Mono.just(id);
                })

                .flatMap(countryService::findById)
                .flatMap(country ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(country)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

     /*
                        return Mono.delay(Duration.ofSeconds(10))
                                .then(countryService.findById(id))
                                .flatMap(country ->
                                        ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(country)
                                );

                         */

    public Mono<ServerResponse> findByIds(ServerRequest request) {

        List<String> idsParams = request.queryParams().get("ids");

        if (idsParams == null || idsParams.isEmpty()) {
            return Mono.error(
                    new InvalidPathVariableException("Query param 'ids' is required")
            );
        }

        Collection<Long> ids;
        try {
            ids = idsParams.stream()
                    .map(String::trim)
                    .map(Long::valueOf)
                    .collect(Collectors.toSet());
        } catch (NumberFormatException ex) {
            return Mono.error(
                    new InvalidPathVariableException(
                            "Invalid country ids: " + idsParams
                    )
            );
        }

        return countryService.findByIds(ids)
                .collectList()
                .flatMap(countries ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(countries)
                );
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return countryService.findAll()
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> findByName(ServerRequest request) {
        String name = request.queryParam("name").orElse("");

        return countryService.findByName(name)
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(CountryRequest.class)
                .flatMap(countryService::save)
                .flatMap(saved ->
                        ServerResponse.status(201)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(saved)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(CountryRequest.class)
                .flatMap(req -> countryService.update(id, req))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated));
    }

    @PreAuthorize("hasAuthority('SCOPE_countries.update.code')")
    public Mono<ServerResponse> updateCode(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(CountryRequest.class)
                .flatMap(req -> countryService.updateCode(id, req.code()))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return countryService.delete(id)
                .then(ServerResponse.ok().build());
    }
}