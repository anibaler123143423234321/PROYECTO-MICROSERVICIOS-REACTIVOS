package pe.edu.galaxy.training.java.ws.api.biblioteca.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

//@Configuration
public class AuthHeaderDebugConfig {

    @Bean
    public WebFilter authHeaderLogger() {
        return (exchange, chain) -> {
            String auth = exchange.getRequest()
                    .getHeaders()
                    .getFirst("Authorization");

            System.out.println("[COUNTRIES-MS] Authorization header = "+(auth != null ? "Exists" : "No Exists"));

            return chain.filter(exchange);
        };
    }
}