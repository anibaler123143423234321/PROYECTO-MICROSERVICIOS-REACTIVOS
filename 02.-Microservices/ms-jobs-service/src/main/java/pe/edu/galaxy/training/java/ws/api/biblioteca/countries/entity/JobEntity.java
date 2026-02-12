package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity;


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
@Table(name = "jobs")
public class JobEntity {

	@Id // PK
    @Column("job_id")
	private Long id;
    
	@Column("name")
    private String name;

    @Column("description")
    private String description;

    private String state;
   
}
