package pe.edu.galaxy.training.java.ms.rrhh.employees.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.edu.galaxy.training.java.ms.rrhh.commons.errors.client.ExternalServiceException;
import pe.edu.galaxy.training.java.ms.rrhh.commons.exceptions.ServiceException;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.ContactRequest;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.CountryResponse;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.EmployeeRequest;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.EmployeeResponse;
import pe.edu.galaxy.training.java.ms.rrhh.employees.entity.EmployeeEntity;
import pe.edu.galaxy.training.java.ms.rrhh.employees.mapper.EmployeeMapper;
import pe.edu.galaxy.training.java.ms.rrhh.employees.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import pe.edu.galaxy.training.java.ms.rrhh.employees.service.client.CountryClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static pe.edu.galaxy.training.java.ms.rrhh.employees.service.EmployeeErrorMessages.*;
import static pe.edu.galaxy.training.java.ms.rrhh.employees.service.EmployeeHandlerException.handleException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final CountryClient countryClient;

    @Override
    public Flux<EmployeeResponse> findAll() {
        return employeeRepository.findByName("")
                /*
                .map(employeeMapper::toDto)
            .onErrorMap(e -> new ServiceException(ERROR_LIST_EMPLOYEES, e));
                 */
                .collectList()
                .flatMapMany(employees -> {
                    Set<Long> countryIds = employees.stream()
                            .map(EmployeeEntity::getCountryId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    Mono<Map<Long, CountryResponse>> countriesMapMono =
                            countryClient.findByIds(countryIds)
                                    .collectMap(CountryResponse::id);

                    return countriesMapMono.flatMapMany(countriesMap ->
                            Flux.fromIterable(employees)
                                    .map(employee -> {
                                        CountryResponse country =
                                                countriesMap.get(employee.getCountryId());

                                        return employeeMapper.toDto(employee, country);
                                    })
                    );
                })
                .onErrorMap(e -> {
                    if (e instanceof ServiceException) {
                        return e;
                    }
                    return new ServiceException(ERROR_LIST_EMPLOYEES, e);
                });
    }
    @Override
    public Mono<EmployeeResponse> findById(Long id) {
        return employeeRepository.findById(id)

                .flatMap(employee ->
                        countryClient.findById(employee.getCountryId())
                                .map(country ->
                                        employeeMapper.toDto(employee, country)
                                )
                )/*
                .flatMap(employee ->
                        Mono.fromFuture(
                                        countryClient.findById(employee.getCountryId())
                                )
                                .map(country ->
                                        employeeMapper.toDto(employee, country)
                                )
                )*/

                .switchIfEmpty(Mono.error(
                new ServiceException(String.format(EMPLOYEE_NOT_FOUND_BY_ID, id))
            ))
                .onErrorMap(ex -> {
                    //log.error("ERROR REAL EN EMPLOYEE SERVICE", ex);
                    if (ex instanceof ExternalServiceException) {
                        return ex;
                    }
                    return new ServiceException(ERROR_FIND_EMPLOYEE_BY_ID, ex);
                });
    }

    @Override
    public Flux<EmployeeResponse> findByName(String name) {
        return employeeRepository.findByName( (name == null) ? "" : name.trim())
                /*
                .flatMap(e ->
                        countryClient.findById(e.getCountryId())
                                .map(c -> employeeMapper.toDto(e, c))
                )*/
                //.map(employeeMapper::toDto)
                .collectList()
                .flatMapMany(employees -> {
                    Set<Long> countryIds = employees.stream()
                            .map(EmployeeEntity::getCountryId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    Mono<Map<Long, CountryResponse>> countriesMapMono =
                            Flux.fromIterable(countryIds)
                                    .flatMap(countryClient::findById)
                                    /*
                                    .flatMap(id ->
                                            Mono.fromFuture(countryClient.findById(id))
                                    )*/
                                    .collectMap(CountryResponse::id);

                    return countriesMapMono.flatMapMany(countriesMap ->
                            Flux.fromIterable(employees)
                                    .map(employee -> {
                                        CountryResponse country =
                                                countriesMap.get(employee.getCountryId());

                                        return employeeMapper.toDto(employee, country);
                                    })
                    );
                })
                .onErrorMap(e -> new ServiceException(ERROR_FIND_EMPLOYEES_BY_NAME, e));
    }

    @Override
    public Mono<Void> save(EmployeeRequest employeeRequest) {
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeRequest);
        employeeEntity.setState("1");
        return employeeRepository.save(employeeEntity)
                .then()
                .onErrorMap(ex -> {
                    return  handleException(ex,
                            new ContactRequest(employeeRequest.phone(),employeeRequest.email()),
                            ERROR_SAVE_EMPLOYEE,null);
        });
    }

    @Override
    public Mono<Void> update(Long id, EmployeeRequest employeeRequest) {
    	 return employeeRepository.findById(id)
    		        .switchIfEmpty(Mono.error(
    		            new ServiceException(
    		                String.format(EMPLOYEE_NOT_FOUND_BY_ID, id))
    		        ))
    		        .flatMap(entity -> {
    		            entity.setFirstName(employeeRequest.firstName());
    		            entity.setLastName(employeeRequest.lastName());
    		            entity.setPhone(employeeRequest.phone());
                        entity.setEmail(employeeRequest.email());
                        entity.setCountryId(employeeRequest.countryId());
    		            employeeRepository.save(entity);
                        return Mono.empty();
    		        })
                 .then()
                 .onErrorMap(ex -> {
                     return  handleException(ex,
                             new ContactRequest(employeeRequest.phone(),employeeRequest.email()),
                             ERROR_UPDATE_EMPLOYEE,id);
    		        });
    }

    @Override
    public Mono<Void> updateContact(Long id, ContactRequest contactRequest) {
        return employeeRepository.findById(id)
            .switchIfEmpty(Mono.error(
                new ServiceException(String.format(EMPLOYEE_NOT_FOUND_BY_ID, id))
            ))
            .flatMap(entity -> {
                entity.setPhone(contactRequest.phone());
                entity.setEmail(contactRequest.email());
                employeeRepository.save(entity);
                return Mono.empty();
            })
                .then()
                .onErrorMap(ex -> {
                    return handleException(ex,
                            contactRequest,
                            ERROR_UPDATE_EMPLOYEE,
                            id);

            });
    }

    @Override
    public Mono<Void> delete(Long id) {
        return employeeRepository.findById(id)
            .switchIfEmpty(Mono.error(
                new ServiceException(String.format(EMPLOYEE_NOT_FOUND_BY_ID, id))
            ))
            .flatMap(l -> employeeRepository.deleteByIdCustom(id))
            .then()
            .onErrorMap(e -> new ServiceException(String.format(ERROR_DELETE_EMPLOYEE,id), e));
    }

}

