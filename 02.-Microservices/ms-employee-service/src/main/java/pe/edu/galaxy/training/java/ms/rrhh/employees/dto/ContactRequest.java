package pe.edu.galaxy.training.java.ms.rrhh.employees.dto;

import jakarta.validation.constraints.*;

public record ContactRequest(

    @NotBlank(message = "Phone is required")
    String phone,

    @NotBlank(message = "Email is required")
    @Email
    String email

) {}