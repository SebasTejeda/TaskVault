package pe.com.lista_tareas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.lista_tareas.entities.Authority;
import pe.com.lista_tareas.entities.AuthorityName;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthorityName(AuthorityName authorityName);
}
