package pe.com.lista_tareas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate today;
    private LocalDate dueDate;

    private Boolean is_it_done;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Task(Long id, String title, String description, Boolean is_it_done, LocalDate today, LocalDate dueDate, Client client) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.is_it_done = is_it_done;
        this.today = today;
        this.dueDate = dueDate;
        this.client = client;
    }
}
