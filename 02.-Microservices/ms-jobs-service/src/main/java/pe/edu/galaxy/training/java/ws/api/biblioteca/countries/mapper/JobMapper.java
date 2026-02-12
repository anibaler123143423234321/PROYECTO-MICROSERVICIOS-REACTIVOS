package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.mapper;

import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.JobRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.JobResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.JobEntity;

public interface JobMapper {

    JobEntity toEntity(JobRequest request);

    JobResponse toDto(JobEntity jobEntity);

}
