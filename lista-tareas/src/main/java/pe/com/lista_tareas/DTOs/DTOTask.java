package pe.com.lista_tareas.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DTOTask {
    private String client;
    private String title;
    private String description;
    private LocalDate today;
    private LocalDate dueDate;
    private Boolean is_it_done;
}
