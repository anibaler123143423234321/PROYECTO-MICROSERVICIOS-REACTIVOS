package pe.edu.galaxy.training.java.ws.api.biblioteca.category.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.entity.CategoryEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<CategoryEntity, Long> {

    @Query("SELECT * FROM category WHERE name ILIKE :name AND state='1'")
    Flux<CategoryEntity> findByName(String name);

    @Query("UPDATE category SET state='0' WHERE category_id=:id")
    Mono<Void> deleteByIdCustom(Long id);
}
