package pe.edu.galaxy.training.java.ms.rrhh.commons.errors.client;

public enum ExternalService {
    COUNTRIES("countries-service"),
    DEPARTMENTS("departments-service");

    private final String serviceName;

    ExternalService(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
