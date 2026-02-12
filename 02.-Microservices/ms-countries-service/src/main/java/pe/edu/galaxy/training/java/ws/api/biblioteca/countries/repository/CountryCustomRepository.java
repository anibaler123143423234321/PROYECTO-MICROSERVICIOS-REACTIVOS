package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.repository;

import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.CountryEntity;
import reactor.core.publisher.Flux;

import java.util.Collection;

public interface CountryCustomRepository {
    Flux<CountryEntity> findByIds(Collection<Long> ids);
}
