package pe.edu.galaxy.training.java.ms.rrhh.commons.errors.client;

public final class CountriesServiceErrors {

    private static final String SERVICE = ExternalService.COUNTRIES.getServiceName();

    private CountriesServiceErrors() {
    }

    public static ExternalServiceException serviceUnavailable() {
        return new ExternalServiceException(
                "COUNTRIES_SERVICE_UNAVAILABLE",
                SERVICE,
                "Countries service is unavailable"
        );
    }

    public static ExternalServiceException clientError() {
        return new ExternalServiceException(
                "COUNTRIES_CLIENT_ERROR",
                SERVICE,
                "Invalid request to countries service"
        );
    }

    public static ExternalServiceException timeout() {
        return new ExternalServiceException(
                "COUNTRIES_SERVICE_TIMEOUT",
                SERVICE,
                "Countries service did not respond in time"
        );
    }

    public static ExternalServiceException countryNotFound(Long id) {
        return new ExternalServiceException(
                "COUNTRY_NOT_FOUND",
                SERVICE,
                String.format("Country with id %d does not exist", id)
        );
    }
}