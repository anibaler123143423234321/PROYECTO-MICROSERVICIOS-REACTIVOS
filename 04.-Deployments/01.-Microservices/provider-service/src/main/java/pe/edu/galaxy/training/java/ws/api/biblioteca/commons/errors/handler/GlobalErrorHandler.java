package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.ApiErrorResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.DuplicateResourceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.ErrorCode;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.InvalidPathVariableException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.client.ExternalServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.ProviderServiceException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@Order(-2)
@Slf4j
@RequiredArgsConstructor
public class GlobalErrorHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;

    private static final String MSG_INTERNAL_ERROR ="An unexpected internal error has occurred";

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        HttpStatus status = resolveStatus(ex);

        ApiErrorResponse error = ApiErrorResponse.builder()
                .code(resolveCode(ex))
                .message(resolveMessage(ex))
                .details(ex.getMessage())
                .path(exchange.getRequest().getPath().value())
                .timestamp(LocalDateTime.now())
                .build();

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(error);
            return exchange.getResponse()
                    .writeWith(Mono.just(
                            exchange.getResponse()
                                    .bufferFactory()
                                    .wrap(bytes)));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    private HttpStatus resolveStatus(Throwable ex) {

        if (ex instanceof InvalidPathVariableException) {
            return HttpStatus.BAD_REQUEST;
        }

        if (ex instanceof ProviderServiceException) {
            return HttpStatus.BAD_REQUEST;
        }

        if (ex instanceof DuplicateResourceException) {
            return HttpStatus.CONFLICT;
        }

        if (ex instanceof ExternalServiceException ese) {
            if (ese.getCode().endsWith("_CLIENT_ERROR")) {
                return HttpStatus.BAD_REQUEST;
            }

            if (ese.getCode().endsWith("_NOT_FOUND")) {
                return HttpStatus.NOT_FOUND;
            }
            if (ese.getCode().endsWith("_TIMEOUT")) {
                return HttpStatus.GATEWAY_TIMEOUT;
            }
            return HttpStatus.SERVICE_UNAVAILABLE;
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String resolveCode(Throwable ex) {

        if (ex instanceof InvalidPathVariableException) {
            return ErrorCode.VALIDATION_ERROR.name();
        }

        if (ex instanceof ProviderServiceException) {
            return ErrorCode.VALIDATION_ERROR.name();
        }

        if (ex instanceof DuplicateResourceException) {
            return ErrorCode.RESOURCE_ALREADY_EXISTS.name();
        }

        if (ex instanceof ExternalServiceException ese) {
            return ese.getCode();
        }

        return ErrorCode.INTERNAL_ERROR.name();
    }

    private String resolveMessage(Throwable ex) {

        if (ex instanceof InvalidPathVariableException) {
            return "Validation error";
        }

        if (ex instanceof ProviderServiceException) {
            return "Validation error";
        }

        if (ex instanceof DuplicateResourceException) {
            return "The resource already exists";
        }

        if (ex instanceof ExternalServiceException ese) {

            if (ese.getCode().endsWith("_CLIENT_ERROR")) {
                return String.format(
                        "External service %s rejected the request",
                        ese.getServiceName()
                );
            }

            if (ese.getCode().endsWith("_NOT_FOUND")) {
                return String.format(
                        "Resource not found in external service %s",
                        ese.getServiceName()
                );
            }
            if (ese.getCode().endsWith("_TIMEOUT")) {
                return String.format(
                        "External service %s did not respond in time",
                        ese.getServiceName()
                );
            }
            return String.format(
                    "External service %s is unavailable",
                    ese.getServiceName()
            );
        }
        return "An unexpected internal error has occurred";
    }

    private void logError(Throwable ex, HttpStatus status) {

        if (status.is5xxServerError()) {
            log.error("Unhandled server error", ex);
        } else {
            log.warn("Handled error [{}]: {}", status, ex.getMessage());
        }
    }
}
