package pe.edu.galaxy.training.java.ws.api.biblioteca.category.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.InvalidPathVariableException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto.CategoryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.service.CategoryService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CategoryHandler {

    private final CategoryService categoryService;

    public Mono<ServerResponse> findById(ServerRequest request) {
        String idRaw = request.pathVariable("id");
        long id;
        try {
            id = Long.parseLong(idRaw);
        } catch (NumberFormatException e) {
            throw new InvalidPathVariableException("Invalid category id: " + idRaw);
        }

        return categoryService.findById(id)
                .flatMap(item -> ServerResponse.ok().bodyValue(item));
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return categoryService.findAll()
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> findByName(ServerRequest request) {
        String name = request.queryParam("name").orElse("");
        return categoryService.findByName(name)
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(CategoryRequest.class)
                .flatMap(categoryService::save)
                .then(ServerResponse.status(201).build());
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return request.bodyToMono(CategoryRequest.class)
                .flatMap(req -> categoryService.update(id, req))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));
        return categoryService.delete(id)
                .then(ServerResponse.ok().build());
    }
}