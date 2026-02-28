package pe.edu.galaxy.training.java.ws.api.biblioteca.stock.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.edu.galaxy.training.java.ws.api.biblioteca.stock.controller.handler.StockHandler;

@Configuration
public class StockRouter {

    @Bean
    RouterFunction<ServerResponse> stockRoutes(StockHandler handler) {

        return RouterFunctions.route()
                .path("/api/v1/stock", builder -> builder
                		
                        .GET("/all", handler::findAll)

                        .GET("/{id}", handler::findById)

                        .POST("", handler::save)
                        .PUT("/{id}", handler::update)
                        .PATCH("/{id}", handler::updateStock)
                        .DELETE("/{id}", handler::delete)
                )
                .build();
    }
}
