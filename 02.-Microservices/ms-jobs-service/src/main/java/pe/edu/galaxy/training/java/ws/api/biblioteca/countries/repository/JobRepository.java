package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.JobEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface JobRepository extends ReactiveCrudRepository<JobEntity, Long> {

	@Query("""
        SELECT *
        FROM jobs
        WHERE UPPER(name) LIKE UPPER(CONCAT('%', :name, '%'))
          AND state = '1'
    """)
	Flux<JobEntity> findByName(String name);

	@Query("""
        UPDATE jobs
        SET state = '0'
        WHERE job_id = :id
    """)
	Mono<Integer> deleteByIdCustom(Long id);
}
