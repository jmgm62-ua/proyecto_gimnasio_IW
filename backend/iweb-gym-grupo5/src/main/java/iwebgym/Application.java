package iwebgym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Habilita la ejecución de tareas programadas
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
