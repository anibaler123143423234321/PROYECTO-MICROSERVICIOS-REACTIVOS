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

	@Column("product_id")
    private Long productId;

    @Column("quantity")
    private Integer quantity;

    @Column("location")
    private String location;

    @Column("provider_id")
    private Long providerId;

    @Column("state")
    private String state;

}
