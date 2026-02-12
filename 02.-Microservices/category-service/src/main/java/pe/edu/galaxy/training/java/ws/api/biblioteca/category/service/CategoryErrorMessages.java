package pe.edu.galaxy.training.java.ws.api.biblioteca.category.service;

public class CategoryErrorMessages {
    private CategoryErrorMessages() {}

    public static final String ERROR_LIST_CATEGORY = "Failed to list categories";
    public static final String ERROR_FIND_CATEGORY_BY_ID = "Failed to search category by id";
    public static final String ERROR_FIND_CATEGORY_BY_NAME = "Failed to search categories by name";
    public static final String CATEGORY_NOT_FOUND_BY_ID = "Category with id %s does not exist";
    public static final String ERROR_SAVE_CATEGORY = "Failed to save category";
    public static final String ERROR_UPDATE_CATEGORY = "Failed to update category with id %d";
    public static final String ERROR_DELETE_CATEGORY = "Failed to delete category with id %d";
}
