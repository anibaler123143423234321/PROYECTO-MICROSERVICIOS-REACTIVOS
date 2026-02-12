package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.entity;

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
@Table(name = "inventory")
public class InventoryEntity {

	@Id // PK
    @Column("inventory_id")
	private Long id;
    
	@Column("name")
    private String name;

	@Column("description")
    private String description;

    @Column("sku")
    private String sku;

    @Column("location")
    private String location;

    @Column("provider_id")
    private Long providerId;

    @Column("state")
    private String state;
   
}
