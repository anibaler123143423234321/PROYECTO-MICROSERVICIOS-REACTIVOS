package pe.edu.galaxy.training.java.ws.api.biblioteca.category.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.DuplicateResourceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.CategoryServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.dto.CategoryRequest;

import java.util.Map;

public class CategoryHandlerException {

    private static final Map<String, String> CONSTRAINT_MAPPINGS = Map.of(
            "uq_category_name", "A category with this name already exists"
    );

    public static Throwable handleException(Throwable ex, CategoryRequest request, String defaultMessage, Long id) {
        String msg = ex.getMessage();
        if (msg != null) {
            for (Map.Entry<String, String> entry : CONSTRAINT_MAPPINGS.entrySet()) {
                if (msg.contains(entry.getKey())) {
                    return new DuplicateResourceException(entry.getValue());
                }
            }
        }
        return new CategoryServiceException(
                id != null ? String.format(defaultMessage, id) : defaultMessage,
                ex
        );
    }
}
