package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.entity.StockEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StockRepository extends ReactiveCrudRepository<StockEntity, Long> {

    @Query("SELECT * FROM stock WHERE name ILIKE :name AND state='1'")
    Flux<StockEntity> findByName(String name);

    @Query("SELECT * FROM stock WHERE state='1'")
    Flux<StockEntity> findAllActive();

    @Query("UPDATE stock SET state='0' WHERE stock_id=:id")
    Mono<Void> deleteByIdCustom(Long id);
}
