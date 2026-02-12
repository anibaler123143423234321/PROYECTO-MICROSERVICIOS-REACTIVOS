package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.CountryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.CountryResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.CountryEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface CountryService {
	
	Mono<CountryResponse> findById(Long id);

	Flux<CountryResponse> findAll();
	
	Flux<CountryResponse> findByName(String name);
	
	Mono<CountryResponse> save(CountryRequest country);
	
	Mono<CountryResponse> update(Long id, CountryRequest country);
	
	Mono<CountryResponse> updateCode(Long id, String code);
	
	Mono<Void> delete(Long id);

	Flux<CountryResponse> findByIds(Collection<Long> ids);
}
