package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockDetailRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StockService {
	
	Mono<StockResponse> findById(Long id);

	Flux<StockResponse> findAll();
	
	Flux<StockResponse> findByName(String name);
	
	Mono<Void> save(StockRequest stockRequest);
	
	Mono<Void> update(Long id, StockRequest stockRequest);
	
	Mono<Void> updateStock(Long id, StockDetailRequest stockDetailRequest);
	
	Mono<Void> delete(Long id);

}
