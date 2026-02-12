package pe.edu.galaxy.training.java.ms.rrhh.employees.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import pe.edu.galaxy.training.java.ms.rrhh.employees.entity.EmployeeEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface EmployeeRepository extends ReactiveCrudRepository<EmployeeEntity, Long> {

	@Query("""
        SELECT *
        FROM employees
        WHERE
        	(UPPER(first_name) LIKE UPPER(CONCAT('%', :name, '%'))
        	 or UPPER(last_name) LIKE UPPER(CONCAT('%', :name, '%')))
          AND state = '1'
    """)
	Flux<EmployeeEntity> findByName(String name);

	@Query("""
        UPDATE employees
        SET state = '0'
        WHERE employee_id = :id
    """)
	Mono<Integer> deleteByIdCustom(Long id);

}
