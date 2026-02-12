package pe.edu.galaxy.training.java.ms.rrhh.employees.service;

import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.ContactRequest;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.EmployeeRequest;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.EmployeeResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
	
	Mono<EmployeeResponse> findById(Long id);

	Flux<EmployeeResponse> findAll();
	
	Flux<EmployeeResponse> findByName(String name);
	
	Mono<Void> save(EmployeeRequest employeeRequest);
	
	Mono<Void> update(Long id, EmployeeRequest employeeRequest);
	
	Mono<Void> updateContact(Long id, ContactRequest contactRequest);
	
	Mono<Void> delete(Long id);

}
