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
@Table(name = "countries")
public class CountryEntity {

	@Id // PK
    @Column("country_id")
	private Long id;
    
	@Column("name")
    private String name;

	@Column("code")
    private String code;

    private String state;
   
}
