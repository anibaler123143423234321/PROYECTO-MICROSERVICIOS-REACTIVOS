package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.CountryEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Repository
public interface CountryRepository extends ReactiveCrudRepository<CountryEntity, Long> {

	@Query("""
        SELECT *
        FROM countries
        WHERE UPPER(name) LIKE UPPER(CONCAT('%', :name, '%'))
          AND state = '1'
    """)
	Flux<CountryEntity> findByName(String name);

	@Query("""
        UPDATE countries
        SET state = '0'
        WHERE country_id = :id
    """)
	Mono<Integer> deleteByIdCustom(Long id);
}
