package pe.edu.galaxy.training.java.ms.rrhh.employees.mapper;

import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.CountryResponse;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.EmployeeRequest;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.EmployeeResponse;
import pe.edu.galaxy.training.java.ms.rrhh.employees.entity.EmployeeEntity;

public interface EmployeeMapper {

    EmployeeEntity toEntity(EmployeeRequest employeeRequest);

    EmployeeResponse toDto(EmployeeEntity employeeEntity, CountryResponse countryResponse);

    EmployeeResponse toDto(EmployeeEntity employeeEntity);
}
