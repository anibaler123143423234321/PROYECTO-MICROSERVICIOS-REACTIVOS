package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProductResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProductClientImpl implements ProductClient {

    private final WebClient webClient;

    public ProductClientImpl(@Value("${service.products.url}") String productUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(productUrl)
                .build();
    }

    @Override
    public Mono<ProductResponse> findById(Long id) {
        return webClient.get()
                .uri("/api/v1/products/{id}", id)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .onErrorResume(e -> {
                    log.error("Error al obtener producto {}: {}", id, e.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Flux<ProductResponse> findByIds(java.util.Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }
        String idsParam = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/products/by-ids")
                        .queryParam("ids", idsParam)
                        .build())
                .retrieve()
                .bodyToFlux(ProductResponse.class)
                .onErrorResume(e -> {
                    log.error("Error al obtener productos multiples {}: {}", idsParam, e.getMessage());
                    return Flux.empty();
                });
    }
}
