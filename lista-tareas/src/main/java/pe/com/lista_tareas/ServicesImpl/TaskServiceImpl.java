package pe.com.lista_tareas.ServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.lista_tareas.DTOs.DTOTask;
import pe.com.lista_tareas.Exceptions.ResourceNotFoundException;
import pe.com.lista_tareas.Repository.ClientRepository;
import pe.com.lista_tareas.Repository.TaskRepository;
import pe.com.lista_tareas.Services.TaskService;
import pe.com.lista_tareas.entities.Client;
import pe.com.lista_tareas.entities.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<DTOTask> listTasks() {
        List<DTOTask> tasks = new ArrayList<>();

        for(Task task : taskRepository.findAll()) {
            String clientFullName = task.getClient().getName() + " " + task.getClient().getLastName();
            String title = task.getTitle();
            String description = task.getDescription();
            Boolean is_it_done = task.getIs_it_done();
            LocalDate currentDate = task.getToday();
            LocalDate dueDate = task.getDueDate();
            DTOTask dtoTask = new DTOTask(clientFullName, title, description, currentDate, dueDate, is_it_done);
            tasks.add(dtoTask);
        }

        return tasks;
    }

    @Override
    @Transactional
    public Task save(Task task) {
        Long clientId = task.getClient().getId();

        if(clientId == null){
            throw new ResourceNotFoundException("Client not found");
        }

        Client exisitingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        task.setClient(exisitingClient);

        return taskRepository.save(task);
    }

    @Override
    public List<Task> findTasksByClientId(Long clientId) {
        return taskRepository.findByClient_Id(clientId);
    }

    @Override
    public Task findTaskById(Long id) {
        Task taskFound = taskRepository.findById(id).orElse(null);
        if(taskFound == null){
            throw new ResourceNotFoundException("Task not found");
        }
        return taskFound;
    }

    @Override
    public void deleteTaskById(Long id) {
        taskRepository.delete(findTaskById(id));
    }

    @Override
    public boolean isTaskDone(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        return task.getIs_it_done();
    }
}
