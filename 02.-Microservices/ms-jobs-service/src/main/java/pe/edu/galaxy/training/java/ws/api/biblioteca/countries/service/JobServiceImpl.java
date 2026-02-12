package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.DuplicateResourceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.ServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.JobRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.JobResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.JobEntity;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.mapper.JobMapper;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.repository.JobRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static pe.edu.galaxy.training.java.ws.api.biblioteca.countries.service.JobHandlerException.mapDuplicateConstraint;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Override
    public Flux<JobResponse> findAll() {
        return jobRepository.findByName("")
                .map(jobMapper::toDto)
            .onErrorMap(e -> new ServiceException("Error al listar los coutries", e));
    }
    @Override
    public Mono<JobResponse> findById(Long id) {
        return jobRepository.findById(id)
                .map(jobMapper::toDto)
            .switchIfEmpty(Mono.error(
                new ServiceException(String.format("No existe libro con el id %s", id))
            ))
            .onErrorMap(e -> new ServiceException("Error al buscar libro por id", e));
    }

    @Override
    public Flux<JobResponse> findByName(String name) {
        return jobRepository.findByName( (name == null) ? "" : name.trim())
                .map(jobMapper::toDto)
                .onErrorMap(e -> new ServiceException("Error al buscar coutries por name", e));
    }

    @Override
    public Mono<JobResponse> save(JobRequest jobRequest) {
        JobEntity jobEntity = jobMapper.toEntity(jobRequest);
        jobEntity.setState("1");
        return jobRepository.save(jobEntity)
        .map(jobMapper::toDto)
                .onErrorMap(ex -> {

                    String msg = ex.getMessage();
            if (msg != null) {
                if (msg.contains("uq_jobs_name")) {
                    return new DuplicateResourceException("name", jobEntity.getName());
                }
            }
            return ex;
        });
    }

    @Override
    public Mono<JobResponse> update(Long id, JobRequest country) {
        // Indepontente
    	 return jobRepository.findById(id)
    		        .switchIfEmpty(Mono.error(
    		            new ServiceException(
    		                String.format("Job not found with id = %d", id))
    		        ))
    		        .flatMap(entity -> {
    		            entity.setName(country.name());
    		            entity.setDescription(country.description());
    		            return jobRepository.save(entity)
                                .map(jobMapper::toDto);
    		        })
    		        .onErrorMap(ex -> {
    		            Throwable throwable = mapDuplicateConstraint(
    		                    ex,
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
    public Mono<Void> delete(Long id) {
        return jobRepository.findById(id)
            .switchIfEmpty(Mono.error(
                new ServiceException(String.format("No existe pais con el id %s", id))
            ))
            .flatMap(l -> jobRepository.deleteByIdCustom(id))
            .then()
            .onErrorMap(e -> new ServiceException(String.format("Error al eliminar el pais, no existe pais con el id = %d",id), e));
    }

}

