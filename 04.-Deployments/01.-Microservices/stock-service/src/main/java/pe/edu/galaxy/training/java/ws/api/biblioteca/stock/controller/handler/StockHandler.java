package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.InvalidPathVariableException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockDetailRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.StockService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StockHandler {

    private final StockService stockService;

    public Mono<ServerResponse> findById(ServerRequest request) {
    	 
    	String idRaw = request.pathVariable("id");

    	    long id;
    	    try {
    	        id = Long.parseLong(idRaw);
    	    } catch (NumberFormatException e) {
    	    	  throw new InvalidPathVariableException("Invalid stock id: " + idRaw);
    	    }

    	    return stockService.findById(id)
    	            .flatMap(item -> ServerResponse.ok().bodyValue(item))
    	            .switchIfEmpty(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return stockService.findAll()
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> findByName(ServerRequest request) {
        String name = request.queryParam("name").orElse("");

        return stockService.findByName(name)
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(StockRequest.class)
                .flatMap(stockService::save)
                .then(ServerResponse.status(201).build());
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(StockRequest.class)
                .flatMap(req -> stockService.update(id, req))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> updateStock(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(StockDetailRequest.class)
                .flatMap(req -> stockService.updateStock(id, req))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return stockService.delete(id)
                .then(ServerResponse.ok().build());
    }
}
