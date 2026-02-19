package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.service;

public class ProviderErrorMessages {
    private ProviderErrorMessages() {
    }

    public static final String ERROR_LIST_PROVIDER =
            "Failed to list providers";

    public static final String ERROR_FIND_PROVIDER_BY_ID =
            "Failed to search provider by id";

    public static final String ERROR_FIND_PROVIDER_BY_NAME =
            "Failed to search providers by name";

    public static final String PROVIDER_NOT_FOUND_BY_ID =
            "Provider with id %s does not exist";

    public static final String ERROR_SAVE_PROVIDER =
            "Failed to save provider";

    public static final String ERROR_UPDATE_PROVIDER =
            "Failed to update provider with id %d";

    public static final String ERROR_DELETE_PROVIDER =
            "Failed to delete provider with id %d";
}
