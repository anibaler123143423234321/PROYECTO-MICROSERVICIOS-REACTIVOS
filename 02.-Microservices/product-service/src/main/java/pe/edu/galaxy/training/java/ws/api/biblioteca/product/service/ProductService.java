package pe.edu.galaxy.training.java.ws.api.biblioteca.product.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.ProductRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.ProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface ProductService {
	
	Mono<ProductResponse> findById(Long id);

	Flux<ProductResponse> findAll();
	
	Flux<ProductResponse> findByName(String name);
	
	Mono<ProductResponse> save(ProductRequest productRequest);
	
	Mono<ProductResponse> update(Long id, ProductRequest productRequest);
	
	Mono<Void> delete(Long id);



}
