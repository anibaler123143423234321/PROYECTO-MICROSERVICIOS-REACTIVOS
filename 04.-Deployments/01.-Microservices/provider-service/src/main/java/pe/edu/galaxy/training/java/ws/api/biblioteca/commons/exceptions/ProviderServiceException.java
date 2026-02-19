package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions;

public class ProviderServiceException extends RuntimeException {
    public ProviderServiceException(String message) {
        super(message);
    }
    public ProviderServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
