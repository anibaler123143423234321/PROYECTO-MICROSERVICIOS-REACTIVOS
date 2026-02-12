package pe.edu.galaxy.training.java.ms.rrhh.employees.service;

import pe.edu.galaxy.training.java.ms.rrhh.commons.errors.core.DuplicateResourceException;
import pe.edu.galaxy.training.java.ms.rrhh.commons.exceptions.ServiceException;
import pe.edu.galaxy.training.java.ms.rrhh.employees.dto.ContactRequest;

import static java.util.Objects.isNull;

public class EmployeeHandlerException {

    public static Throwable handleException(
            Throwable ex,
            ContactRequest contactRequest,
            String errorMessage,
            Long id
           ) {
        Throwable mapped = mapDuplicateConstraint(ex, contactRequest.phone(), contactRequest.email());

        if (mapped instanceof DuplicateResourceException) {
            return mapped;
        }
        if (isNull(id)) {
            return new ServiceException(errorMessage, ex);
        }else{
            return new ServiceException(String.format(errorMessage, id), ex);
        }
    }

    public static Throwable mapDuplicateConstraint(
            Throwable ex,
            String phone,
            String email) {

        String msg = ex.getMessage();

        if (msg != null) {

            if (msg.contains("uq_employees_phone")) {
                return new DuplicateResourceException("phone", phone);
            }

            if (msg.contains("uq_employees_email")) {
                return new DuplicateResourceException("email", email);
            }
        }

        return ex;
    }


}
