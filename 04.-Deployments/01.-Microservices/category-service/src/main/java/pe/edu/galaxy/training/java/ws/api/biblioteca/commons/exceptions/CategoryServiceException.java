package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions;

public class CategoryServiceException extends RuntimeException {
    public CategoryServiceException(String message) {
        super(message);
    }
    public CategoryServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
