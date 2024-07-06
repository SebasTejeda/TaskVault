package pe.com.lista_tareas.Services;

import pe.com.lista_tareas.DTOs.DTOUser;
import pe.com.lista_tareas.entities.User;

public interface UserService {
    User findById(Long id);

    User findClientByUsername(String username);

    User register(DTOUser user);

}
