package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.DuplicateResourceException;

public class ContryHandlerException {
    public static Throwable mapDuplicateConstraint(
            Throwable ex,
            String code,
            String name) {

        String msg = ex.getMessage();

        if (msg != null) {

            if (msg.contains("uq_countries_code")) {
                return new DuplicateResourceException("code", code);
            }

            if (msg.contains("uq_countries_name")) {
                return new DuplicateResourceException("name", name);
            }
        }

        return ex;
    }
}
