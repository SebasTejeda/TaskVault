package pe.com.lista_tareas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.lista_tareas.DTOs.DTOTask;
import pe.com.lista_tareas.Services.TaskService;
import pe.com.lista_tareas.entities.Task;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/tasks")
    public ResponseEntity<List<DTOTask>> getTasks() {
        List<DTOTask> dtoTasks = taskService.listTasks();
        return new ResponseEntity<>(dtoTasks, HttpStatus.OK);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task taskfound = taskService.findTaskById(id);
        return new ResponseEntity<>(taskfound, HttpStatus.OK);
    }

    @PostMapping("/task/create")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task newTask = taskService.save(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @GetMapping("task/client/{clientId}")
    public ResponseEntity<List<Task>> getTaskByClientId(@PathVariable Long clientId) {
        List<Task> tasks = taskService.findTasksByClientId(clientId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @DeleteMapping("task/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/task/{id}/done")
    public ResponseEntity<Boolean> isTaskDone(@PathVariable Long id) {
        boolean isDone = taskService.isTaskDone(id);
        return new ResponseEntity<>(isDone, HttpStatus.OK);
    }
}
