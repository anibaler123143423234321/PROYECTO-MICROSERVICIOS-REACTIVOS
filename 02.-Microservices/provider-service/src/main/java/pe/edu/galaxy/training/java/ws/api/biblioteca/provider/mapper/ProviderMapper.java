package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.mapper;

import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto.ProviderRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto.ProviderResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.entity.ProviderEntity;

public interface ProviderMapper {

    ProviderEntity toEntity(ProviderRequest providerRequest);

    ProviderResponse toDto(ProviderEntity providerEntity);
}
