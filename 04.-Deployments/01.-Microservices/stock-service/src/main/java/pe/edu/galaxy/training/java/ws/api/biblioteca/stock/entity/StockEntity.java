package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.entity;

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
@Table(name = "stock")
public class StockEntity {

	@Id // PK
    @Column("stock_id")
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
