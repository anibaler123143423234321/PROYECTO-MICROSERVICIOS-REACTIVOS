package pe.edu.galaxy.training.java.ws.api.biblioteca;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CategoryServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CategoryServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Category Service Running OK");
    }
}