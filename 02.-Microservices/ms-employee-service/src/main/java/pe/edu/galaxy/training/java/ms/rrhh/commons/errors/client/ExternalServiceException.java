package pe.edu.galaxy.training.java.ms.rrhh.commons.errors.client;

public class ExternalServiceException extends RuntimeException {

    private final String code;
    private final String serviceName;

    public ExternalServiceException(
            String code,
            String serviceName,
            String message
    ) {
        super(message);
        this.code = code;
        this.serviceName = serviceName;
    }

    public ExternalServiceException(
            String code,
            String serviceName,
            String message,
            Throwable cause
    ) {
        super(message, cause);
        this.code = code;
        this.serviceName = serviceName;
    }

    public String getCode() {
        return code;
    }

    public String getServiceName() {
        return serviceName;
    }
}
