package pe.com.lista_tareas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.com.lista_tareas.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByClient_Id(Long client_id);
}
