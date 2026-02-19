package pe.edu.galaxy.training.java.ws.api.biblioteca.provider.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.provider.controller.handler.ProviderHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProviderRouter {

    @Bean
    public RouterFunction<ServerResponse> providerRoutes(ProviderHandler handler) {
        return route(GET("/api/v1/providers/all"), handler::findAll)
                .andRoute(GET("/api/v1/providers/by-name"), handler::findByName)
                .andRoute(GET("/api/v1/providers/by-ids"), handler::findByIds)
                .andRoute(GET("/api/v1/providers/{id}"), handler::findById)
                .andRoute(POST("/api/v1/providers"), handler::save)
                .andRoute(PUT("/api/v1/providers/{id}"), handler::update)
                .andRoute(DELETE("/api/v1/providers/{id}"), handler::delete);
    }
}
