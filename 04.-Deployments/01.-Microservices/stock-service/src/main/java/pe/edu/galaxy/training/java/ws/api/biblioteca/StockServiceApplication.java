package pe.edu.galaxy.training.java.ws.api.biblioteca;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("stock Service Running OK");
	}
}
