package pe.edu.galaxy.training.java.ms.rrhh;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MsEmployeeApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(MsEmployeeApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Runing ok");

	}


}