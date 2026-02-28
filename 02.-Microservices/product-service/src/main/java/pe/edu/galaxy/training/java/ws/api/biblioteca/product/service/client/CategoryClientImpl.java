package pe.edu.galaxy.training.java.ws.api.biblioteca.product.service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.galaxy.training.java.ws.api.biblioteca.product.dto.CategoryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CategoryClientImpl implements CategoryClient {

    private final WebClient webClient;

    public CategoryClientImpl(@Value("${service.categories.url}") String categoryUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(categoryUrl)
                .build();
    }

    @Override
    public Mono<CategoryResponse> findById(Long id) {
        return webClient.get()
                .uri("/api/v1/categories/{id}", id)
                .retrieve()
                .bodyToMono(CategoryResponse.class)
                .onErrorResume(e -> {
                    log.error("Error al obtener categoria {}: {}", id, e.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Flux<CategoryResponse> findByIds(List<Long> ids) {
        String idsParam = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/categories/by-ids")
                        .queryParam("ids", idsParam)
                        .build())
                .retrieve()
                .bodyToFlux(CategoryResponse.class)
                .onErrorResume(e -> {
                    log.error("Error al obtener categorias multiples {}: {}", idsParam, e.getMessage());
                    return Flux.empty();
                });
    }
}
