package pe.com.lista_tareas.Services;

import pe.com.lista_tareas.DTOs.DTOTask;
import pe.com.lista_tareas.entities.Task;

import java.util.List;

public interface TaskService {

    List<DTOTask> listTasks();

    Task save(Task task);

    List<Task> findTasksByClientId(Long clientId);
    Task findTaskById(Long id);
    void deleteTaskById(Long id);

    boolean isTaskDone(Long id);
}
