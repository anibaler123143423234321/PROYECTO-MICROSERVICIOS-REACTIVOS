package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.mapper;

import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.JobRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.JobResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.JobEntity;

@Component
public class JobMapperImpl implements JobMapper {
    @Override
    public JobEntity toEntity(JobRequest request) {
        return JobEntity
                .builder()
                .name(request.name())
                .description(request.description())
                .build();
    }

    @Override
    public JobResponse toDto(JobEntity jobEntity) {
        return JobResponse
                .builder()
                .id(jobEntity.getId())
                .name(jobEntity.getName().toUpperCase())
                .description(jobEntity.getDescription())
                .build();
    }

}
