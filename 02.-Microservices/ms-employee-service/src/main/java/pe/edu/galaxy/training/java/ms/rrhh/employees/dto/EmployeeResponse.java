package pe.edu.galaxy.training.java.ms.rrhh.employees.dto;


import lombok.Builder;

@Builder
public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String phone,
        String email,
        //Long countryId
        CountryResponse country
) {
}