package pe.edu.galaxy.training.java.ws.api.biblioteca.countries.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.edu.galaxy.training.java.ws.api.biblioteca.countries.controller.handler.CountryHandler;

@Configuration
public class CountryRouter {

	// Swagger
    @Bean
    RouterFunction<ServerResponse> countryRoutes(CountryHandler handler) {

        return RouterFunctions.route()
                .path("/api/v1/countries", builder -> builder

                        .GET("/all", handler::findAll)
                        .GET("/by-name", handler::findByName)
                        .GET("/ids", handler::findByIds)

                        .GET("/{id}", handler::findById)

                        .POST("", handler::save)
                        .PUT("/{id}", handler::update)
                        .PATCH("/{id}/code", handler::updateCode)
                        .DELETE("/{id}", handler::delete)
                )
                .build();
    }
}