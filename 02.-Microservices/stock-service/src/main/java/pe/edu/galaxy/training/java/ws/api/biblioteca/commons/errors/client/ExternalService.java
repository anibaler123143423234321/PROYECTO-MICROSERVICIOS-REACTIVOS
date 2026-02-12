package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.client;

public enum ExternalService {
    PROVIDERS("provider-service"),
    DEPARTMENTS("departments-service");

    private final String serviceName;

    ExternalService(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
