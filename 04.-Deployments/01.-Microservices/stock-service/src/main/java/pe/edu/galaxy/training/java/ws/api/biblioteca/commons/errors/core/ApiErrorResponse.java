package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorResponse {

    private String code;
    private String message;
    private String details;
    private String path;
    private LocalDateTime timestamp;

}
