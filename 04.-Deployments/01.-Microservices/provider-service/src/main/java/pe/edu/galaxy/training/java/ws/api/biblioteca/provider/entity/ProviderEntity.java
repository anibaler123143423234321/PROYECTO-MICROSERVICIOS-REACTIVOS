package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "provider")
public class ProviderEntity {

    @Id
    @Column("provider_id")
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("phone")
    private String phone;

    @Column("email")
    private String email;

    @Column("state")
    private String state;
}
