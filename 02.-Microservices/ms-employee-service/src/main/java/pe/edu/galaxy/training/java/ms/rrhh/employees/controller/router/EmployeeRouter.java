package pe.edu.galaxy.training.java.ms.rrhh.employees.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import pe.edu.galaxy.training.java.ms.rrhh.employees.controller.handler.EmployeeHandler;

@Configuration
public class EmployeeRouter {

	// Swagger
    @Bean
    RouterFunction<ServerResponse> countryRoutes(EmployeeHandler handler) {

        return RouterFunctions.route()
                .path("/api/v1/employees", builder -> builder
                		
                        .GET("/all", handler::findAll)
                        .GET("/by-name", handler::findByName)

                        .GET("/{id}", handler::findById)

                        .POST("", handler::save)
                        .PUT("/{id}", handler::update)
                        .PATCH("/{id}", handler::updateContact)
                        .DELETE("/{id}", handler::delete)
                )
                .build();
    }
}