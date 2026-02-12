package pe.edu.galaxy.training.java.ms.ms_server_admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class MsServerAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsServerAdminApplication.class, args);
	}

}
