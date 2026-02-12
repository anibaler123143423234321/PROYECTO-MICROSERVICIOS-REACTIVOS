package pe.edu.galaxy.training.java.ms.rrhh.employees.mapper;

import org.springframework.stereotype.Component;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.CountryResponse;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.EmployeeRequest;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.EmployeeResponse;
import pe.edu.galaxy.training.java.ms.rrhh.employees.entity.EmployeeEntity;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public EmployeeEntity toEntity(EmployeeRequest employeeRequest) {
        return EmployeeEntity
                .builder()
                .firstName(employeeRequest.firstName())
                .lastName(employeeRequest.lastName())
                .phone(employeeRequest.phone())
                .email(employeeRequest.email())
                .countryId(employeeRequest.countryId())
                .build();
    }

    @Override
    public EmployeeResponse toDto(EmployeeEntity employeeEntity,
            CountryResponse countryResponse) {
        return EmployeeResponse
                .builder()
                .id(employeeEntity.getId())
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .phone(employeeEntity.getPhone())
                .email(employeeEntity.getEmail())
                //.countryId(employeeEntity.getCountryId())
                .country(countryResponse)
                .build();
    }

    @Override
    public EmployeeResponse toDto(EmployeeEntity employeeEntity) {
        return EmployeeResponse
                .builder()
                .id(employeeEntity.getId())
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .phone(employeeEntity.getPhone())
                .email(employeeEntity.getEmail())
                //.countryId(employeeEntity.getCountryId())
                //.country(countryResponse)
                .build();
    }
}
