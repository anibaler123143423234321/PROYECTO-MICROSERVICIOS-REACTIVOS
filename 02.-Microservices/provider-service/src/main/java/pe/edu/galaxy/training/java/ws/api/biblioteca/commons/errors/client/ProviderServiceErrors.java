package pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.client;

public final class ProviderServiceErrors {

    private static final String SERVICE = ExternalService.PROVIDERS.getServiceName();

    private ProviderServiceErrors() {
    }

    public static ExternalServiceException serviceUnavailable() {
        return new ExternalServiceException(
                "PROVIDER_SERVICE_UNAVAILABLE",
                SERVICE,
                "Provider service is unavailable"
        );
    }

    public static ExternalServiceException clientError() {
        return new ExternalServiceException(
                "PROVIDER_CLIENT_ERROR",
                SERVICE,
                "Invalid request to provider service"
        );
    }

    public static ExternalServiceException timeout() {
        return new ExternalServiceException(
                "PROVIDER_SERVICE_TIMEOUT",
                SERVICE,
                "Provider service did not respond in time"
        );
    }

    public static ExternalServiceException providerNotFound(Long id) {
        return new ExternalServiceException(
                "PROVIDER_NOT_FOUND",
                SERVICE,
                String.format("Provider with id %d does not exist", id)
        );
    }
}
