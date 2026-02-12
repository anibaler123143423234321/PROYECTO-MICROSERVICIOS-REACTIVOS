package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.entity.ProviderEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProviderRepository extends ReactiveCrudRepository<ProviderEntity, Long> {

    @Query("SELECT * FROM provider WHERE name ILIKE :name AND state='1'")
    Flux<ProviderEntity> findByName(String name);

    @Query("UPDATE provider SET state='0' WHERE provider_id=:id")
    Mono<Void> deleteByIdCustom(Long id);
}
