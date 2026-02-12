package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.JobRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.JobResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface JobService {
	
	Mono<JobResponse> findById(Long id);

	Flux<JobResponse> findAll();
	
	Flux<JobResponse> findByName(String name);
	
	Mono<JobResponse> save(JobRequest country);
	
	Mono<JobResponse> update(Long id, JobRequest country);
	
	Mono<Void> delete(Long id);



}
