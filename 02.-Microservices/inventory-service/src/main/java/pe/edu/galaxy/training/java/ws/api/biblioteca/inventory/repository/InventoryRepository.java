package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.entity.InventoryEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface InventoryRepository extends ReactiveCrudRepository<InventoryEntity, Long> {

    @Query("SELECT * FROM inventory WHERE name ILIKE :name AND state='1'")
    Flux<InventoryEntity> findByName(String name);

    @Query("UPDATE inventory SET state='0' WHERE inventory_id=:id")
    Mono<Void> deleteByIdCustom(Long id);
}
