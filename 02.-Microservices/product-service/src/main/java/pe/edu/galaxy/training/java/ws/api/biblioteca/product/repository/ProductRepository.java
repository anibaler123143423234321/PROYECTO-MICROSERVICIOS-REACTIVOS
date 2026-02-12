package pe.edu.galaxy.training.java.ws.api.biblioteca.product.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import pe.edu.galaxy.training.java.ws.api.biblioteca.product.entity.ProductEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {

	@Query("""
        SELECT *
        FROM product
        WHERE UPPER(name) LIKE UPPER(CONCAT('%', :name, '%'))
          AND state = '1'
    """)
	Flux<ProductEntity> findByName(String name);

	@Query("""
        UPDATE product
        SET state = '0'
        WHERE product_id = :id
    """)
	Mono<Integer> deleteByIdCustom(Long id);
}
