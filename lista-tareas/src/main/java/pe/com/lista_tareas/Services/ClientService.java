package pe.com.lista_tareas.Services;

import pe.com.lista_tareas.DTOs.DTOUser;
import pe.com.lista_tareas.entities.Client;

import java.util.List;

public interface ClientService {
    List<Client> listAll();

    Client findById(Long id);

    Client findByUsername(String username);

    Client save(Client client);

    Client save(DTOUser dtoUser);
}
