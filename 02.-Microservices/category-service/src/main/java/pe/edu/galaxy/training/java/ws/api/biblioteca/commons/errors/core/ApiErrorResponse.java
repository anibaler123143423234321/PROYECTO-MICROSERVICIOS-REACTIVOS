package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ApiErrorResponse {
    private ErrorCode code;
    private String message;
    private LocalDateTime timestamp;
}
