package pe.edu.galaxy.training.java.ws.api.biblioteca;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
public class MsJobApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MsJobApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Runing ok");
	}
}