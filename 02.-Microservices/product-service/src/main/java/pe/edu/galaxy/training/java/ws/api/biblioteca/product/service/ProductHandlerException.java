package pe.edu.galaxy.training.java.ws.api.biblioteca.product.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.DuplicateResourceException;

public class ProductHandlerException {
    public static Throwable mapDuplicateConstraint(
            Throwable ex,
            String name) {

        String msg = ex.getMessage();

        if (msg != null) {
            if (msg.contains("uq_product_name")) {
                return new DuplicateResourceException("name", name);
            }
        }

        return ex;
    }
}
