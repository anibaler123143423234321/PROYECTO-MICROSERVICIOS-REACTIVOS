package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.ApiErrorResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.ErrorCode;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.DuplicateResourceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.InvalidPathVariableException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.CategoryServiceException;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(CategoryServiceException.class)
    public ResponseEntity<ApiErrorResponse> handleCategoryServiceException(CategoryServiceException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex.getMessage().contains("does not exist")) {
            status = HttpStatus.NOT_FOUND;
        }
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(ErrorCode.INTERNAL_ERROR)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(ErrorCode.DUPLICATE_RESOURCE)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPathVariableException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidPathVariableException(InvalidPathVariableException ex) {
        ApiErrorResponse response = ApiErrorResponse.builder()
                .code(ErrorCode.INVALID_PATH_VARIABLE)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}