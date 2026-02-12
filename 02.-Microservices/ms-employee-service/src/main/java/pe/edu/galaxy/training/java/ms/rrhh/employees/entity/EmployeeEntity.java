package pe.edu.galaxy.training.java.ms.rrhh.employees.entity;


import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class EmployeeEntity {

	@Id // PK
    @Column("employee_id")
	private Long id;
    
	@Column("first_name")
    private String firstName;

	@Column("last_name")
    private String lastName;

    @Column("phone")
    private String phone;

    @Column("email")
    private String email;

    @Column("country_id")
    private Long countryId;

    @Column("state")
    private String state;
   
}
