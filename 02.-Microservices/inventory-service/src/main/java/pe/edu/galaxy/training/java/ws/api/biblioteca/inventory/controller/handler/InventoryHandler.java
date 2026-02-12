package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.InvalidPathVariableException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.StockRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.InventoryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.service.InventoryService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryHandler {

    private final InventoryService inventoryService;

    public Mono<ServerResponse> findById(ServerRequest request) {
    	 
    	String idRaw = request.pathVariable("id");

    	    long id;
    	    try {
    	        id = Long.parseLong(idRaw);
    	    } catch (NumberFormatException e) {
    	    	  throw new InvalidPathVariableException("Invalid inventory id: " + idRaw);
    	    }

    	    return inventoryService.findById(id)
    	            .flatMap(item -> ServerResponse.ok().bodyValue(item))
    	            .switchIfEmpty(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return inventoryService.findAll()
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> findByName(ServerRequest request) {
        String name = request.queryParam("name").orElse("");

        return inventoryService.findByName(name)
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(InventoryRequest.class)
                .flatMap(inventoryService::save)
                .then(ServerResponse.status(201).build());
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(InventoryRequest.class)
                .flatMap(req -> inventoryService.update(id, req))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> updateStock(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(StockRequest.class)
                .flatMap(req -> inventoryService.updateStock(id, req))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return inventoryService.delete(id)
                .then(ServerResponse.ok().build());
    }
}
