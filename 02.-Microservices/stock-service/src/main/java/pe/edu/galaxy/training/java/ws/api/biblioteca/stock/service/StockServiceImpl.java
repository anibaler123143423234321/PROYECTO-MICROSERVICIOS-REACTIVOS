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

import static pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.StockErrorMessages.*;
import static pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service.StockHandlerException.handleException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final ProviderClient providerClient;

    @Override
    public Flux<StockResponse> findAll() {
        return stockRepository.findAllActive()
                .collectList()
                .flatMapMany(items -> {
                    Set<Long> providerIds = items.stream()
                            .map(StockEntity::getProviderId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    Mono<Map<Long, ProviderResponse>> providersMapMono =
                            providerClient.findByIds(providerIds)
                                    .collectMap(ProviderResponse::id);

                    return providersMapMono.flatMapMany(providersMap ->
                            Flux.fromIterable(items)
                                    .map(item -> {
                                        ProviderResponse provider =
                                                providersMap.get(item.getProviderId());

                                        return stockMapper.toDto(item, provider);
                                    })
                    );
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
                .flatMap(item ->
                        providerClient.findById(item.getProviderId())
                                .map(provider ->
                                        stockMapper.toDto(item, provider)
                                )
                )
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
    public Flux<StockResponse> findByName(String name) {
        return stockRepository.findByName((name == null) ? "" : name.trim())
                .collectList()
                .flatMapMany(items -> {
                    Set<Long> providerIds = items.stream()
                            .map(StockEntity::getProviderId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    Mono<Map<Long, ProviderResponse>> providersMapMono =
                            Flux.fromIterable(providerIds)
                                    .flatMap(providerClient::findById)
                                    .collectMap(ProviderResponse::id);

                    return providersMapMono.flatMapMany(providersMap ->
                            Flux.fromIterable(items)
                                    .map(item -> {
                                        ProviderResponse provider =
                                                providersMap.get(item.getProviderId());

                                        return stockMapper.toDto(item, provider);
                                    })
                    );
                })
                .onErrorMap(e -> new StockServiceException(ERROR_FIND_STOCK_BY_NAME, e));
    }

    @Override
    public Mono<Void> save(StockRequest stockRequest) {
        StockEntity stockEntity = stockMapper.toEntity(stockRequest);
        stockEntity.setState("1");
        return stockRepository.save(stockEntity)
                .then()
                .onErrorMap(ex -> handleException(ex,
                        new StockDetailRequest(stockRequest.sku(), stockRequest.location()),
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
                    entity.setName(stockRequest.name());
                    entity.setDescription(stockRequest.description());
                    entity.setSku(stockRequest.sku());
                    entity.setLocation(stockRequest.location());
                    entity.setProviderId(stockRequest.providerId());
                    return stockRepository.save(entity);
                })
                .then()
                .onErrorMap(ex -> handleException(ex,
                        new StockDetailRequest(stockRequest.sku(), stockRequest.location()),
                        ERROR_UPDATE_STOCK, id));
    }

    @Override
    public Mono<Void> updateStock(Long id, StockDetailRequest stockDetailRequest) {
        return stockRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new StockServiceException(String.format(STOCK_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(entity -> {
                    entity.setSku(stockDetailRequest.sku());
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
