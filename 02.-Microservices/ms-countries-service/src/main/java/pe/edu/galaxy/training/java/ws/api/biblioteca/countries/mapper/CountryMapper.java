package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.mapper;

import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.CountryRequest;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.dto.CountryResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.CountryEntity;

public interface CountryMapper {

    CountryEntity toEntity(CountryRequest request);

    CountryResponse toDto(CountryEntity countryEntity);

    //Flux<CountryResponse> toDto(Flux<CountryEntity> country);
}
