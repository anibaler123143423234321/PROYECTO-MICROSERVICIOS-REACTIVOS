package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {

    private String field;
    private String value;

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String field, String value) {
        super(String.format("El valor '%s' ya existe para el campo '%s'", value, field));
        this.field = field;
        this.value = value;
    }
}
