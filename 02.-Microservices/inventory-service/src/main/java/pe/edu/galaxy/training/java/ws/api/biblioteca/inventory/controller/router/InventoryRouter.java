package pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.edu.galaxy.training.java.ws.api.biblioteca.inventory.controller.handler.InventoryHandler;

@Configuration
public class InventoryRouter {

    @Bean
    RouterFunction<ServerResponse> inventoryRoutes(InventoryHandler handler) {

        return RouterFunctions.route()
                .path("/api/v1/inventory", builder -> builder
                		
                        .GET("/all", handler::findAll)
                        .GET("/by-name", handler::findByName)

                        .GET("/{id}", handler::findById)

                        .POST("", handler::save)
                        .PUT("/{id}", handler::update)
                        .PATCH("/{id}", handler::updateStock)
                        .DELETE("/{id}", handler::delete)
                )
                .build();
    }
}
