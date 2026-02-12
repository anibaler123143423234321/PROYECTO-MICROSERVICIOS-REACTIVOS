package pe.edu.galaxy.training.java.ws.api.biblioteca.product.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.edu.galaxy.training.java.ws.api.biblioteca.product.controller.handler.ProductHandler;

@Configuration
public class ProductRouter {

	// Swagger
    @Bean
    RouterFunction<ServerResponse> productRoutes(ProductHandler handler) {

        return RouterFunctions.route()
                .path("/api/v1/products", builder -> builder

                        .GET("/all", handler::findAll)
                        .GET("/by-name", handler::findByName)

                        .GET("/{id}", handler::findById)

                        .POST("", handler::save)
                        .PUT("/{id}", handler::update)
                        .DELETE("/{id}", handler::delete)
                )
                .build();
    }
}
