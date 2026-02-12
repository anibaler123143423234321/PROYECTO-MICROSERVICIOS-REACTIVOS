package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.ProviderServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto.ProviderRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto.ProviderResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.mapper.ProviderMapper;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.repository.ProviderRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

import static pe.edu.galaxy.training.java.ws.api.biblioteca.provider.service.ProviderErrorMessages.*;
import static pe.edu.galaxy.training.java.ws.api.biblioteca.provider.service.ProviderHandlerException.handleException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private final ProviderRepository providerRepository;
    private final ProviderMapper providerMapper;

    @Override
    public Flux<ProviderResponse> findAll() {
        return providerRepository.findAll()
                .filter(p -> "1".equals(p.getState()))
                .map(providerMapper::toDto)
                .onErrorMap(e -> new ProviderServiceException(ERROR_LIST_PROVIDER, e));
    }

    @Override
    public Flux<ProviderResponse> findByName(String name) {
        return providerRepository.findByName((name == null) ? "" : name.trim())
                .map(providerMapper::toDto)
                .onErrorMap(e -> new ProviderServiceException(ERROR_FIND_PROVIDER_BY_NAME, e));
    }

    @Override
    public Mono<ProviderResponse> findById(Long id) {
        return providerRepository.findById(id)
                .map(providerMapper::toDto)
                .switchIfEmpty(Mono.error(
                        new ProviderServiceException(String.format(PROVIDER_NOT_FOUND_BY_ID, id))
                ))
                .onErrorMap(e -> {
                    if (e instanceof ProviderServiceException) return e;
                    return new ProviderServiceException(ERROR_FIND_PROVIDER_BY_ID, e);
                });
    }

    @Override
    public Flux<ProviderResponse> findByIds(Set<Long> ids) {
        return providerRepository.findAllById(ids)
                .filter(p -> "1".equals(p.getState()))
                .map(providerMapper::toDto);
    }

    @Override
    public Mono<Void> save(ProviderRequest providerRequest) {
        var entity = providerMapper.toEntity(providerRequest);
        entity.setState("1");
        return providerRepository.save(entity)
                .then()
                .onErrorMap(e -> handleException(e, providerRequest, ERROR_SAVE_PROVIDER, null));
    }

    @Override
    public Mono<Void> update(Long id, ProviderRequest providerRequest) {
        return providerRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ProviderServiceException(String.format(PROVIDER_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(entity -> {
                    entity.setName(providerRequest.name());
                    entity.setDescription(providerRequest.description());
                    entity.setPhone(providerRequest.phone());
                    entity.setEmail(providerRequest.email());
                    return providerRepository.save(entity);
                })
                .then()
                .onErrorMap(e -> handleException(e, providerRequest, ERROR_UPDATE_PROVIDER, id));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return providerRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ProviderServiceException(String.format(PROVIDER_NOT_FOUND_BY_ID, id))
                ))
                .flatMap(entity -> providerRepository.deleteByIdCustom(id))
                .then()
                .onErrorMap(e -> new ProviderServiceException(String.format(ERROR_DELETE_PROVIDER, id), e));
    }
}
