package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.mapper;

import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto.ProviderRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto.ProviderResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.entity.ProviderEntity;

@Component
public class ProviderMapperImpl implements ProviderMapper {

    @Override
    public ProviderEntity toEntity(ProviderRequest providerRequest) {
        if (providerRequest == null) return null;
        return ProviderEntity.builder()
                .name(providerRequest.name())
                .description(providerRequest.description())
                .phone(providerRequest.phone())
                .email(providerRequest.email())
                .build();
    }

    @Override
    public ProviderResponse toDto(ProviderEntity providerEntity) {
        if (providerEntity == null) return null;
        return ProviderResponse.builder()
                .id(providerEntity.getId())
                .name(providerEntity.getName())
                .description(providerEntity.getDescription())
                .phone(providerEntity.getPhone())
                .email(providerEntity.getEmail())
                .build();
    }
}
