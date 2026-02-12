package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.DuplicateResourceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.ProviderServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.dto.ProviderRequest;

import java.util.Map;

public class ProviderHandlerException {

    private static final Map<String, String> CONSTRAINT_MAPPINGS = Map.of(
            "provider_name_key", "A provider with this name already exists",
            "provider_email_key", "A provider with this email already exists"
    );

    public static Throwable handleException(Throwable ex, ProviderRequest request, String defaultMessage, Long id) {
        String msg = ex.getMessage();
        if (msg != null) {
            for (Map.Entry<String, String> entry : CONSTRAINT_MAPPINGS.entrySet()) {
                if (msg.contains(entry.getKey())) {
                    return new DuplicateResourceException(entry.getValue());
                }
            }
        }
        return new ProviderServiceException(
                id != null ? String.format(defaultMessage, id) : defaultMessage,
                ex
        );
    }
}
