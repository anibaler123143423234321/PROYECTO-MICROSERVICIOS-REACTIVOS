package pe.edu.galaxy.training.java.ms.rrhh.employees.service;

public class EmployeeErrorMessages {
    private EmployeeErrorMessages() {

    }

    public static final String ERROR_LIST_EMPLOYEES =
            "Failed to list employees";

    public static final String ERROR_FIND_EMPLOYEE_BY_ID =
            "Failed to search employee by id";

    public static final String ERROR_FIND_EMPLOYEES_BY_NAME =
            "Failed to search employees by name";

    public static final String EMPLOYEE_NOT_FOUND_BY_ID =
            "Employee with id %s does not exist";

    public static final String ERROR_SAVE_EMPLOYEE =
            "Failed to save employee";

    public static final String ERROR_UPDATE_EMPLOYEE =
            "Failed to update employee with id %d";

    public static final String ERROR_DELETE_EMPLOYEE =
            "Failed to delete employee with id %d";
}
