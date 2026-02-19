package pe.edu.galaxy.training.java.ms.ms_administracion_api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.logging.Logger;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class.getName());

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ex -> ex

                        // ========================
                        // PUBLIC ROUTES (GET)
                        // ========================
                        .pathMatchers(GET, "/products/**").permitAll()
                        .pathMatchers(GET, "/categories/**").permitAll()
                        .pathMatchers(GET, "/providers/**").permitAll()
                        .pathMatchers(GET, "/stock/**").permitAll()

                        // ========================
                        // INFRA
                        // ========================
                        .pathMatchers("/actuator/**").permitAll()

                        // ========================
                        // PROTECTED ROUTES (ROLES)
                        // ========================
                        // STOCK
                        .pathMatchers(POST, "/stock/**").hasAnyRole("jefe_rrhh", "admin")
                        .pathMatchers(PUT, "/stock/**").hasAnyRole("jefe_rrhh", "admin")
                        .pathMatchers(DELETE, "/stock/**").hasAnyRole("jefe_rrhh", "admin")

                        // PRODUCTS
                        .pathMatchers(POST, "/products/**").hasAnyRole("jefe_rrhh", "admin")
                        .pathMatchers(PUT, "/products/**").hasAnyRole("jefe_rrhh", "admin")
                        .pathMatchers(DELETE, "/products/**").hasAnyRole("jefe_rrhh", "admin")

                        // CATEGORIES
                        .pathMatchers(POST, "/categories/**").hasAnyRole("jefe_rrhh", "admin")
                        .pathMatchers(PUT, "/categories/**").hasAnyRole("jefe_rrhh", "admin")
                        .pathMatchers(DELETE, "/categories/**").hasAnyRole("jefe_rrhh", "admin")

                        // PROVIDERS
                        .pathMatchers(POST, "/providers/**").hasAnyRole("jefe_rrhh", "admin")
                        .pathMatchers(PUT, "/providers/**").hasAnyRole("jefe_rrhh", "admin")
                        .pathMatchers(DELETE, "/providers/**").hasAnyRole("jefe_rrhh", "admin")

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter()))
                );

        return http.build();
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthConverter() {

        return jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess != null && realmAccess.get("roles") instanceof Collection<?> roles) {
                roles.forEach(role ->
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + role))
                );
            }
            authorities.forEach(a -> LOGGER.info(a.getAuthority()));
            return Mono.just(new JwtAuthenticationToken(jwt, authorities));
        };
    }
}