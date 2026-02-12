package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.repository;

import io.r2dbc.postgresql.codec.PostgresqlObjectId;
import io.r2dbc.spi.Parameters;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.entity.CountryEntity;
import reactor.core.publisher.Flux;

import java.util.Collection;

@Repository
@RequiredArgsConstructor
public class CountryCustomRepositoryImpl implements CountryCustomRepository {
    private final DatabaseClient databaseClient;

    @Override
    public Flux<CountryEntity> findByIds(Collection<Long> ids) {

        if (ids == null || ids.isEmpty()) {
            return Flux.empty();
        }
        return databaseClient.sql("""
                 SELECT *
                FROM countries
                WHERE country_id = ANY($1)
                  AND state = '1'
                """)
                .bind(
                        0,
                        Parameters.in(
                                PostgresqlObjectId.INT8_ARRAY,
                                ids.toArray(Long[]::new)
                        )
                )
                .map((row, meta) -> CountryEntity.builder()
                        .id(row.get("country_id", Long.class))
                        .code(row.get("code", String.class))
                        .name(row.get("name", String.class))
                        .state(row.get("state",String.class))
                        .build()
                )
                .all();
    }
}