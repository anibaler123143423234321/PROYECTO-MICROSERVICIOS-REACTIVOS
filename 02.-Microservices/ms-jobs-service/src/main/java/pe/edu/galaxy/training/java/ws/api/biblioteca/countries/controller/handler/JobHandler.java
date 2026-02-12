package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.InvalidPathVariableException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.JobRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.service.JobService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JobHandler {

    private final JobService jobService;

    public Mono<ServerResponse> findById(ServerRequest request) {

        return Mono.justOrEmpty(request.pathVariable("id"))

                .flatMap(id -> {
                    try {
                        return Mono.just(Long.parseLong(id));
                    } catch (NumberFormatException ex) {
                        return Mono.error(
                                new InvalidPathVariableException(
                                        "Invalid job id: " + id
                                )
                        );
                    }
                })

                .flatMap(id -> {
                    return Mono.just(id);
                })

                .flatMap(jobService::findById)
                .flatMap(country ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(country)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return jobService.findAll()
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> findByName(ServerRequest request) {
        String name = request.queryParam("name").orElse("");

        return jobService.findByName(name)
                .collectList()
                .flatMap(list -> list.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(list)
                );
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(JobRequest.class)
                .flatMap(jobService::save)
                .flatMap(saved ->
                        ServerResponse.status(201)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(saved)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(JobRequest.class)
                .flatMap(req -> jobService.update(id, req))
                .flatMap(updated -> ServerResponse.ok().bodyValue(updated));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        Long id = Long.valueOf(request.pathVariable("id"));

        return jobService.delete(id)
                .then(ServerResponse.ok().build());
    }
}