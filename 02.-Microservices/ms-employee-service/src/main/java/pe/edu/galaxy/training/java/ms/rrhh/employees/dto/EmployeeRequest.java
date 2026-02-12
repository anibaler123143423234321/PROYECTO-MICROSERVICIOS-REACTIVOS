package pe.edu.galaxy.training.java.ms.rrhh.employees.dto;

import jakarta.validation.constraints.*;

public record EmployeeRequest(
	
	@NotBlank(message = "First Name is required")
    @Size(min = 3, max = 10, message = "First Name must have {min} characters and must not exceed {max} characters")
    String firstName,

    @NotBlank(message = "Last Name is required")
    @Size(min = 3, max = 20, message = "Last Name must have {min} characters and must not exceed {max} characters")
    String lastName,

    @NotBlank(message = "Phone is required")
    String phone,

    @NotBlank(message = "Email is required")
    @Email
    String email,

    @NotEmpty(message = "Country id is required")
    @Positive
    Long countryId

) {}