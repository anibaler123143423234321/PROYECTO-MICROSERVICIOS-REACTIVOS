package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.client.ExternalServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.InventoryServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.StockRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.ProviderResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.InventoryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.InventoryResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.entity.InventoryEntity;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.mapper.InventoryMapper;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.service.client.ProviderClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.service.InventoryErrorMessages.*;
import static pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.service.InventoryHandlerException.handleException;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ProviderClient providerClient;

    @Override
    public Flux<InventoryResponse> findAll() {
        return inventoryRepository.findByName("")
                .collectList()
                .flatMapMany(items -> {
                    Set<Long> providerIds = items.stream()
                            .map(InventoryEntity::getProviderId)
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

                                        return inventoryMapper.toDto(item, provider);
                                    })
                    );
                })
                .onErrorMap(e -> {
                    if (e instanceof InventoryServiceException) {
                        return e;
                    }
                    return new InventoryServiceException(ERROR_LIST_INVENTORY, e);
                });
    }

    @Override
    public Mono<InventoryResponse> findById(Long id) {
        return inventoryRepository.findById(id)
                .flatMap(item ->
                        providerClient.findById(item.getProviderId())
                                .map(provider ->
                                        inventoryMapper.toDto(item, provider)
                                )
                )
                .switchIfEmpty(Mono.error(
                        new InventoryServiceException(String.format(INVENTORY_NOT_FOUND_BY_ID, id))
                ))
                .onErrorMap(ex -> {
                    if (ex instanceof ExternalServiceException) {
                        return ex;
                    }
                    return new InventoryServiceException(ERROR_FIND_INVENTORY_BY_ID, ex);
                });
    }

    @Override
    public Flux<InventoryResponse> findByName(String name) {
        return inventoryRepository.findByName((name == null) ? "" : name.trim())
                .collectList()
                .flatMapMany(items -> {
                    Set<Long> providerIds = items.stream()
                            .map(InventoryEntity::getProviderId)
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

                                        return inventoryMapper.toDto(item, provider);
                                    })
                    );
                })
                .onErrorMap(e -> new InventoryServiceException(ERROR_FIND_INVENTORY_BY_NAME, e));
    }

    @Override
    public Mono<Void> save(InventoryRequest inventoryRequest) {
        InventoryEntity inventoryEntity = inventoryMapper.toEntity(inventoryRequest);
        inventoryEntity.setState("1");
        return inventoryRepository.save(inventoryEntity)
                .then()
                .onErrorMap(ex -> handleException(ex,
                        new StockRequest(inventoryRequest.sku(), inventoryRequest.location()),
                        ERROR_SAVE_INVENTORY, null));
    }

    @Override
    public Mono<Void> update(Long id, InventoryRequest inventoryRequest) {
        return inventoryRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new InventoryServiceException(
                                String.format(INVENTORY_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(entity -> {
                    entity.setName(inventoryRequest.name());
                    entity.setDescription(inventoryRequest.description());
                    entity.setSku(inventoryRequest.sku());
                    entity.setLocation(inventoryRequest.location());
                    entity.setProviderId(inventoryRequest.providerId());
                    return inventoryRepository.save(entity);
                })
                .then()
                .onErrorMap(ex -> handleException(ex,
                        new StockRequest(inventoryRequest.sku(), inventoryRequest.location()),
                        ERROR_UPDATE_INVENTORY, id));
    }

    @Override
    public Mono<Void> updateStock(Long id, StockRequest stockRequest) {
        return inventoryRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new InventoryServiceException(String.format(INVENTORY_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(entity -> {
                    entity.setSku(stockRequest.sku());
                    entity.setLocation(stockRequest.location());
                    return inventoryRepository.save(entity);
                })
                .then()
                .onErrorMap(ex -> handleException(ex,
                        stockRequest,
                        ERROR_UPDATE_INVENTORY,
                        id));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return inventoryRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new InventoryServiceException(String.format(INVENTORY_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(l -> inventoryRepository.deleteByIdCustom(id))
                .then()
                .onErrorMap(e -> new InventoryServiceException(String.format(ERROR_DELETE_INVENTORY, id), e));
    }
}
