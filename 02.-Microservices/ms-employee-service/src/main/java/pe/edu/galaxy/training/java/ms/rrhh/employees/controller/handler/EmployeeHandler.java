package pe.edu.galaxy.training.java.ms.rrhh.employees.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.ms.rrhh.commons.errors.core.InvalidPathVariableException;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.ContactRequest;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.EmployeeRequest;
import pe.edu.galaxy.training.java.ms.rrhh.employees.service.EmployeeService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmployeeHandler {

    private final EmployeeService employeeService;

    public Mono<ServerResponse> findById(ServerRequest request) {
    	 
    	String idRaw = request.pathVariable("id");

    	    long id;
    	    try {
    	        id = Long.parseLong(idRaw);
    	    } catch (NumberFormatException e) {
    	    	  throw new InvalidPathVariableException("Invalid country id: " + idRaw);
    	    }

    	    return employeeService.findById(id)
    	            .flatMap(country -> ServerResponse.ok().bodyValue(country))
    	            .switchIfEmpty(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return employeeService.findAll()
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> findByName(ServerRequest request) {
        String name = request.queryParam("name").orElse("");

        return employeeService.findByName(name)
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(EmployeeRequest.class)
                .flatMap(employeeService::save)
                .flatMap(saved ->
                        ServerResponse.status(201)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(saved)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(EmployeeRequest.class)
                .flatMap(req -> employeeService.update(id, req))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated));
    }

    public Mono<ServerResponse> updateContact(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(ContactRequest.class)
                .flatMap(req -> employeeService.updateContact(id, req))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return employeeService.delete(id)
                .then(ServerResponse.ok().build());
    }
}