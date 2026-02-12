package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.mapper;

import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.CountryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.CountryResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.CountryEntity;

@Component
public class CountryMapperImpl implements CountryMapper{
    @Override
    public CountryEntity toEntity(CountryRequest request) {
        return CountryEntity
                .builder()
                .name(request.name())
                .code(request.code())
                .build();
    }

    @Override
    public CountryResponse toDto(CountryEntity countryEntity) {
        return CountryResponse
                .builder()
                .id(countryEntity.getId())
                .name(countryEntity.getName().toUpperCase())
                .code(countryEntity.getCode())
                .build();
    }
    /*
    @Override
    public Flux<CountryResponse> toDto(Flux<CountryEntity> country) {
        return null;
    }*/
}
