package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.InvalidPathVariableException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto.ProviderRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.service.ProviderService;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ProviderHandler {

    private final ProviderService providerService;

    public Mono<ServerResponse> findById(ServerRequest request) {
        String idRaw = request.pathVariable("id");
        long id;
        try {
            id = Long.parseLong(idRaw);
        } catch (NumberFormatException e) {
            throw new InvalidPathVariableException("Invalid provider id: " + idRaw);
        }

        return providerService.findById(id)
                .flatMap(item -> ServerResponse.ok().bodyValue(item))
                .switchIfEmpty(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> findByIds(ServerRequest request) {
        java.util.List<String> idsParams = request.queryParams().get("ids");
        
        if (idsParams == null || idsParams.isEmpty()) {
            return ServerResponse.ok().bodyValue(Collections.emptyList());
        }

        Set<Long> ids = idsParams.stream()
                .flatMap(s -> Stream.of(s.split(",")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        return providerService.findByIds(ids)
                .collectList()
                .flatMap(list -> ServerResponse.ok().bodyValue(list));
    }


    public Mono<ServerResponse> findAll(ServerRequest request) {
        return providerService.findAll()
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> findByName(ServerRequest request) {
        String name = request.queryParam("name").orElse("");
        return providerService.findByName(name)
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(ProviderRequest.class)
                .flatMap(providerService::save)
                .then(ServerResponse.status(201).build());
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(ProviderRequest.class)
                .flatMap(req -> providerService.update(id, req))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return providerService.delete(id)
                .then(ServerResponse.ok().build());
    }
}
