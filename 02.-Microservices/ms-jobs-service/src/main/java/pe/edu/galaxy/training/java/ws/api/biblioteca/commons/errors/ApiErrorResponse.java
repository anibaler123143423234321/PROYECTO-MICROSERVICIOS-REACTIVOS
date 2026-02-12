package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiErrorResponse {

    private String code;
    private String message;
    private String details;
    private String path;
    private LocalDateTime timestamp;
}
