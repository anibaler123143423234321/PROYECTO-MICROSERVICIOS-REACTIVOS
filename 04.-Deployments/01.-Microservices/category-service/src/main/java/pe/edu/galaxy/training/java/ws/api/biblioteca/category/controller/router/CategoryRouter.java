package pe.edu.galaxy.training.java.ws.api.biblioteca.category.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.edu.galaxy.training.java.ws.api.biblioteca.category.controller.handler.CategoryHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CategoryRouter {

    @Bean
    public RouterFunction<ServerResponse> categoryRoutes(CategoryHandler handler) {
        return route(GET("/api/v1/categories/all"), handler::findAll)
                .andRoute(GET("/api/v1/categories/by-name"), handler::findByName)
                .andRoute(GET("/api/v1/categories/{id}"), handler::findById)
                .andRoute(POST("/api/v1/categories"), handler::save)
                .andRoute(PUT("/api/v1/categories/{id}"), handler::update)
                .andRoute(DELETE("/api/v1/categories/{id}"), handler::delete);
    }
}