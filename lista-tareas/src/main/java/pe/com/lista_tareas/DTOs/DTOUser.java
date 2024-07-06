package pe.com.lista_tareas.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOUser {
    private Long id;
    private String username;
    private String password;
    private String type;
}
