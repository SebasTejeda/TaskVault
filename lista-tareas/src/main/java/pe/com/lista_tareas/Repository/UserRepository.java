package pe.com.lista_tareas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.lista_tareas.entities.Authority;
import pe.com.lista_tareas.entities.AuthorityName;
import pe.com.lista_tareas.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findClientByUsername(String username);
}
