package pe.edu.galaxy.training.java.ms.ms_administracion_api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(ex -> ex
                // ========================
                // PRODUCTS
                // ========================
                .pathMatchers("/products/**").permitAll()

                // ========================
                // CATEGORIES
                // ========================
                .pathMatchers("/categories/**").permitAll()

                // ========================
                // PROVIDERS
                // ========================
                .pathMatchers("/providers/**").permitAll()

                // ========================
                // STOCK
                // ========================
                .pathMatchers("/stock/**").permitAll()

                // ========================
                // INFRA
                // ========================
                .pathMatchers("/actuator/**").permitAll()

                .anyExchange().permitAll()
            );

        return http.build();
    }
}