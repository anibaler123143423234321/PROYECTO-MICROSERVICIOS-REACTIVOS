package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.StockRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.InventoryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.InventoryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InventoryService {
	
	Mono<InventoryResponse> findById(Long id);

	Flux<InventoryResponse> findAll();
	
	Flux<InventoryResponse> findByName(String name);
	
	Mono<Void> save(InventoryRequest inventoryRequest);
	
	Mono<Void> update(Long id, InventoryRequest inventoryRequest);
	
	Mono<Void> updateStock(Long id, StockRequest stockRequest);
	
	Mono<Void> delete(Long id);

}
