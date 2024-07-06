package pe.com.lista_tareas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pe.com.lista_tareas.Repository.AuthorityRepository;
import pe.com.lista_tareas.Repository.ClientRepository;
import pe.com.lista_tareas.Repository.TaskRepository;
import pe.com.lista_tareas.Repository.UserRepository;
import pe.com.lista_tareas.entities.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EntityScan(basePackages = "pe.com.lista_tareas.entities")
public class ListaTareasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListaTareasApplication.class, args);
	}

	@Bean
	public CommandLineRunner mappingDemo(
			UserRepository userRepository,
			AuthorityRepository authorityRepository,
			ClientRepository clientRepository,
			TaskRepository taskRepository
	){
		return args -> {
			authorityRepository.save(new Authority(AuthorityName.ROLE_ADMIN));
			authorityRepository.save(new Authority(AuthorityName.ROLE_CLIENT));
			userRepository.save(new User("maria", new BCryptPasswordEncoder().encode("12345678"),
							List.of(
									authorityRepository.findByAuthorityName(AuthorityName.ROLE_CLIENT)
							)

					)
			);

			userRepository.save(new User("luis", new BCryptPasswordEncoder().encode("12345678"),
							List.of(
									authorityRepository.findByAuthorityName(AuthorityName.ROLE_ADMIN)
							)

					)
			);

			Client client1 = clientRepository.save(new Client(0L, "María","Gutierrez","mgutierrez@gmail.com","987654321","maria", new BCryptPasswordEncoder().encode("12345678")));

			clientRepository.flush();

			Task task1 = taskRepository.save(new Task(0L, "Realizar almuerzo", "Hacer arroz con pollo para la familia", false, LocalDate.of(2024, 7, 5), LocalDate.of(2024, 7, 6), client1));
		};
	}

}
