package pe.edu.galaxy.training.java.ws.api.biblioteca.product.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.InvalidPathVariableException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.ProductRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.service.ProductService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService productService;

    public Mono<ServerResponse> findById(ServerRequest request) {

        return Mono.justOrEmpty(request.pathVariable("id"))

                .flatMap(id -> {
                    try {
                        return Mono.just(Long.parseLong(id));
                    } catch (NumberFormatException ex) {
                        return Mono.error(
                                new InvalidPathVariableException(
                                        "Invalid Product id: " + id
                                )
                        );
                    }
                })

                .flatMap(id -> {
                    return Mono.just(id);
                })

                .flatMap(productService::findById)
                .flatMap(productResponse ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(productResponse)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return productService.findAll()
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> findByName(ServerRequest request) {
        String name = request.queryParam("name").orElse("");

        return productService.findByName(name)
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(ProductRequest.class)
                .flatMap(productService::save)
                .flatMap(saved ->
                        ServerResponse.status(201)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(saved)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(ProductRequest.class)
                .flatMap(req -> productService.update(id, req))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return productService.delete(id)
                .then(ServerResponse.ok().build());
    }
}
