package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.DuplicateResourceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.ServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.CountryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.CountryResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.CountryEntity;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.mapper.CountryMapper;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.repository.CountryCustomRepository;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.repository.CountryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static pe.edu.galaxy.training.java.ws.api.biblioteca.countries.service.ContryHandlerException.mapDuplicateConstraint;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryCustomRepository countryCustomRepository;
    private final CountryMapper countryMapper;

    @Override
    public Flux<CountryResponse> findAll() {
        return countryRepository.findByName("")
                .map(countryMapper::toDto)
            .onErrorMap(e -> new ServiceException("Error al listar los coutries", e));
    }
    @Override
    public Mono<CountryResponse> findById(Long id) {
        return countryRepository.findById(id)
                .map(countryMapper::toDto)
            .switchIfEmpty(Mono.error(
                new ServiceException(String.format("No existe libro con el id %s", id))
            ))
            .onErrorMap(e -> new ServiceException("Error al buscar libro por id", e));
    }

    @Override
    public Flux<CountryResponse> findByName(String name) {
        return countryRepository.findByName( (name == null) ? "" : name.trim())
                .map(countryMapper::toDto)
                .onErrorMap(e -> new ServiceException("Error al buscar coutries por name", e));
    }

    @Override
    public Mono<CountryResponse> save(CountryRequest countryRequest) {
        CountryEntity countryEntity = countryMapper.toEntity(countryRequest);
        countryEntity.setState("1");
        return countryRepository.save(countryEntity)
        .map(countryMapper::toDto)
                .onErrorMap(ex -> {

                    String msg = ex.getMessage();
            if (msg != null) {
                if (msg.contains("uq_countries_code")) {
                    return new DuplicateResourceException("code", countryEntity.getCode());
                }
                if (msg.contains("uq_countries_name")) {
                    return new DuplicateResourceException("name", countryEntity.getName());
                }
            }
            return ex;
        });
    }

    @Override
    public Mono<CountryResponse> update(Long id, CountryRequest country) {
        // Indepontente
    	 return countryRepository.findById(id)
    		        .switchIfEmpty(Mono.error(
    		            new ServiceException(
    		                String.format("No existe país con el id = %d", id))
    		        ))
    		        .flatMap(entity -> {
    		            entity.setName(country.name());
    		            entity.setCode(country.code());
    		            //entity.setState(country.state());
    		            return countryRepository.save(entity)
                                .map(countryMapper::toDto);
    		        })
    		        .onErrorMap(ex -> {
    		            Throwable throwable = mapDuplicateConstraint(
    		                    ex,
    		                    country.code(),
    		                    country.name());
    		            if (throwable instanceof DuplicateResourceException) {
    		                return throwable;
    		            }
    		            return new ServiceException(
    		                String.format("Error al actualizar el país con id = %d", id),
    		                ex);
    		        });
    }

    @Override
    public Mono<CountryResponse> updateCode(Long id, String code) {
        return countryRepository.findById(id)
            .switchIfEmpty(Mono.error(
                new ServiceException(String.format("No existe libro con el id %s", id))
            ))
            .flatMap(existing -> {
                existing.setCode(code);
                return countryRepository.save(existing).map(countryMapper::toDto);
            })
            //.onErrorMap(e -> new ServiceException(String.format("Error al actualizar el codigo del pais, no existe pais con el id = %d",id), e));
            .onErrorMap(ex -> {
                Throwable mapped = mapDuplicateConstraint(
                        ex,
                        code,
                        null);
                if (mapped instanceof DuplicateResourceException) {
                    return mapped;
                }
                return new ServiceException(
                    String.format(
                        "Error al actualizar el código del país con id = %d", id),
                    ex);
            });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return countryRepository.findById(id)
            .switchIfEmpty(Mono.error(
                new ServiceException(String.format("No existe pais con el id %s", id))
            ))
            .flatMap(l -> countryRepository.deleteByIdCustom(id))
            .then()
            .onErrorMap(e -> new ServiceException(String.format("Error al eliminar el pais, no existe pais con el id = %d",id), e));
    }

    @Override
    public Flux<CountryResponse> findByIds(Collection<Long> ids) {
        return countryCustomRepository.findByIds(ids)
                .map(countryMapper::toDto)
                .onErrorMap(e -> {
                    if (e instanceof ServiceException) {
                        return e;
                    }
                        log.error("REAL ERROR searching countries by ids", e);
                    return new ServiceException("Error searching countries by ids", e);
                });
    }

}

