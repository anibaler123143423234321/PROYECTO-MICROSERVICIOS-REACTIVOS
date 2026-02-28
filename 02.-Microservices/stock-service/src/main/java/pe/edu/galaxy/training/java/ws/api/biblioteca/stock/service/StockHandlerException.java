package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service;

import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.errors.core.DuplicateResourceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.commons.exceptions.StockServiceException;
import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.dto.StockDetailRequest;

import static java.util.Objects.isNull;

public class StockHandlerException {

    public static Throwable handleException(
            Throwable ex,
            StockDetailRequest StockDetailRequest,
            String errorMessage,
            Long id
           ) {
        Throwable mapped = mapDuplicateConstraint(ex, StockDetailRequest.quantity(), StockDetailRequest.location());

        if (mapped instanceof DuplicateResourceException) {
            return mapped;
        }
        if (isNull(id)) {
            return new StockServiceException(errorMessage, ex);
        }else{
            return new StockServiceException(String.format(errorMessage, id), ex);
        }
    }

    public static Throwable mapDuplicateConstraint(
            Throwable ex,
            Integer quantity,
            String location) {

        String msg = ex.getMessage();

        if (msg != null) {

            if (msg.contains("uq_stock_product_provider")) {
                return new DuplicateResourceException("product/provider", "Duplicate stock for this product and provider");
            }

            if (msg.contains("uq_stock_location")) {
                return new DuplicateResourceException("location", location);
            }
        }

        return ex;
    }
}
