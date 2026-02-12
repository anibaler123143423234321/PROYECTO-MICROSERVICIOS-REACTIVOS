package pe.edu.galaxy.training.java.ms.rrhh.employees.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import java.time.Duration;

@Configuration
public class GlobalConfig {

    @Bean
    WebClient webClient(WebClient.Builder builder,
                        @Value("${service.countries.url}") String url){
        /*
        return builder
                .baseUrl(url)
                .build();

         */
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(3));

        return builder
                .baseUrl(url)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    };

}
