package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.DuplicateResourceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.InventoryServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.dto.StockRequest;

import static java.util.Objects.isNull;

public class InventoryHandlerException {

    public static Throwable handleException(
            Throwable ex,
            StockRequest stockRequest,
            String errorMessage,
            Long id
           ) {
        Throwable mapped = mapDuplicateConstraint(ex, stockRequest.sku(), stockRequest.location());

        if (mapped instanceof DuplicateResourceException) {
            return mapped;
        }
        if (isNull(id)) {
            return new InventoryServiceException(errorMessage, ex);
        }else{
            return new InventoryServiceException(String.format(errorMessage, id), ex);
        }
    }

    public static Throwable mapDuplicateConstraint(
            Throwable ex,
            String sku,
            String location) {

        String msg = ex.getMessage();

        if (msg != null) {

            if (msg.contains("uq_inventory_sku")) {
                return new DuplicateResourceException("sku", sku);
            }

            if (msg.contains("uq_inventory_location")) {
                return new DuplicateResourceException("location", location);
            }
        }

        return ex;
    }
}
