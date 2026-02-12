package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.service;

public class InventoryErrorMessages {
    private InventoryErrorMessages() {
    }

    public static final String ERROR_LIST_INVENTORY =
            "Failed to list inventory items";

    public static final String ERROR_FIND_INVENTORY_BY_ID =
            "Failed to search inventory item by id";

    public static final String ERROR_FIND_INVENTORY_BY_NAME =
            "Failed to search inventory items by name";

    public static final String INVENTORY_NOT_FOUND_BY_ID =
            "Inventory item with id %s does not exist";

    public static final String ERROR_SAVE_INVENTORY =
            "Failed to save inventory item";

    public static final String ERROR_UPDATE_INVENTORY =
            "Failed to update inventory item with id %d";

    public static final String ERROR_DELETE_INVENTORY =
            "Failed to delete inventory item with id %d";
}
