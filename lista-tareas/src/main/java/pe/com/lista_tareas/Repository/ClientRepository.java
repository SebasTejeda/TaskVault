package pe.com.lista_tareas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.lista_tareas.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findClientByUsername(String username);
}
