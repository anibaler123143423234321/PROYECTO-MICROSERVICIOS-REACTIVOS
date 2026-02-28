package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.client.ExternalServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.StockServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockDetailRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProviderResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.entity.StockEntity;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.mapper.StockMapper;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.client.ProviderClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.ProductResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.client.ProductClient;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.client.ProviderClient;

import static pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.StockErrorMessages.*;
import static pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.StockHandlerException.handleException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final ProviderClient providerClient;
    private final ProductClient productClient;

    @Override
    public Flux<StockResponse> findAll() {
        return stockRepository.findAllActive()
                .collectList()
                .flatMapMany(items -> {
                    Set<Long> providerIds = items.stream()
                            .map(StockEntity::getProviderId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    Set<Long> productIds = items.stream()
                            .map(StockEntity::getProductId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    Mono<Map<Long, ProviderResponse>> providersMapMono =
                            providerClient.findByIds(providerIds)
                                    .onErrorResume(e -> {
                                        log.warn("Error al obtener los proveedores para la lista de stock: {}", e.getMessage());
                                        return Flux.empty();
                                    })
                                    .collectMap(ProviderResponse::id);

                    Mono<Map<Long, ProductResponse>> productsMapMono =
                            productClient.findByIds(productIds)
                                    .onErrorResume(e -> {
                                        log.warn("Error al obtener productos para la lista de stock: {}", e.getMessage());
                                        return Flux.empty();
                                    })
                                    .collectMap(ProductResponse::id);

                    return Mono.zip(providersMapMono, productsMapMono)
                            .flatMapMany(tuple -> {
                                Map<Long, ProviderResponse> providersMap = tuple.getT1();
                                Map<Long, ProductResponse> productsMap = tuple.getT2();

                                return Flux.fromIterable(items)
                                        .map(item -> {
                                            ProviderResponse provider = providersMap.get(item.getProviderId());
                                            ProductResponse product = productsMap.get(item.getProductId());
                                            return stockMapper.toDto(item, product, provider);
                                        });
                            });
                })
                .onErrorMap(e -> {
                    if (e instanceof StockServiceException) {
                        return e;
                    }
                    return new StockServiceException(ERROR_LIST_STOCK, e);
                });
    }

    @Override
    public Mono<StockResponse> findById(Long id) {
        return stockRepository.findById(id)
                .flatMap(item -> {
                    Mono<ProviderResponse> providerMono = Mono.empty();
                    if (item.getProviderId() != null) {
                        providerMono = providerClient.findById(item.getProviderId())
                                .onErrorResume(e -> {
                                    log.warn("Error al obtener el proveedor {} para el stock {}: {}", item.getProviderId(), id, e.getMessage());
                                    return Mono.empty();
                                });
                    }

                    Mono<ProductResponse> productMono = Mono.empty();
                    if (item.getProductId() != null) {
                        productMono = productClient.findById(item.getProductId())
                                .onErrorResume(e -> {
                                    log.warn("Error al obtener producto {} para el stock {}: {}", item.getProductId(), id, e.getMessage());
                                    return Mono.empty();
                                });
                    }

                    return Mono.zip(Mono.just(item), providerMono.defaultIfEmpty(null), productMono.defaultIfEmpty(null))
                            .map(tuple -> stockMapper.toDto(tuple.getT1(), tuple.getT3(), tuple.getT2()));
                })
                .switchIfEmpty(Mono.error(
                        new StockServiceException(String.format(STOCK_NOT_FOUND_BY_ID, id))
                ))

                .onErrorMap(ex -> {
                    if (ex instanceof ExternalServiceException) {
                        return ex;
                    }
                    return new StockServiceException(ERROR_FIND_STOCK_BY_ID, ex);
                });
    }

    @Override
    public Mono<Void> save(StockRequest stockRequest) {
        StockEntity stockEntity = stockMapper.toEntity(stockRequest);
        stockEntity.setState("1");
        return stockRepository.save(stockEntity)
                .then()
                .onErrorMap(ex -> handleException(ex,
                        new StockDetailRequest(stockRequest.quantity(), stockRequest.location()),
                        ERROR_SAVE_STOCK, null));
    }

    @Override
    public Mono<Void> update(Long id, StockRequest stockRequest) {
        return stockRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new StockServiceException(
                                String.format(STOCK_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(entity -> {
                    entity.setProductId(stockRequest.productId());
                    entity.setQuantity(stockRequest.quantity());
                    entity.setLocation(stockRequest.location());
                    entity.setProviderId(stockRequest.providerId());
                    return stockRepository.save(entity);
                })
                .then()
                .onErrorMap(ex -> handleException(ex,
                        new StockDetailRequest(stockRequest.quantity(), stockRequest.location()),
                        ERROR_UPDATE_STOCK, id));
    }

    @Override
    public Mono<Void> updateStock(Long id, StockDetailRequest stockDetailRequest) {
        return stockRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new StockServiceException(String.format(STOCK_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(entity -> {
                    entity.setQuantity(stockDetailRequest.quantity());
                    entity.setLocation(stockDetailRequest.location());
                    return stockRepository.save(entity);
                })
                .then()
                .onErrorMap(ex -> handleException(ex,
                        stockDetailRequest,
                        ERROR_UPDATE_STOCK,
                        id));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return stockRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new StockServiceException(String.format(STOCK_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(l -> stockRepository.deleteByIdCustom(id))
                .then()
                .onErrorMap(e -> new StockServiceException(String.format(ERROR_DELETE_STOCK, id), e));
    }
}
