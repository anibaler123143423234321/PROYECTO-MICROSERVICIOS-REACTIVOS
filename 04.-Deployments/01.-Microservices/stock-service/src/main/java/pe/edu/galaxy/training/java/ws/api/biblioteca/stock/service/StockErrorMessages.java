package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.service;

public class StockErrorMessages {
    private StockErrorMessages() {
    }

    public static final String ERROR_LIST_STOCK =
            "Failed to list stock items";

    public static final String ERROR_FIND_STOCK_BY_ID =
            "Failed to search stock item by id";

    public static final String ERROR_FIND_STOCK_BY_NAME =
            "Failed to search stock items by name";

    public static final String STOCK_NOT_FOUND_BY_ID =
            "Stock item with id %s does not exist";

    public static final String ERROR_SAVE_STOCK =
            "Failed to save stock item";

    public static final String ERROR_UPDATE_STOCK =
            "Failed to update stock item with id %d";

    public static final String ERROR_DELETE_STOCK =
            "Failed to delete stock item with id %d";
}
