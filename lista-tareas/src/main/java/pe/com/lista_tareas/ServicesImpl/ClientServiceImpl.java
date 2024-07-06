package pe.com.lista_tareas.ServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.com.lista_tareas.DTOs.DTOUser;
import pe.com.lista_tareas.Exceptions.IncompleteDataException;
import pe.com.lista_tareas.Exceptions.ResourceNotFoundException;
import pe.com.lista_tareas.Repository.AuthorityRepository;
import pe.com.lista_tareas.Repository.ClientRepository;
import pe.com.lista_tareas.Services.ClientService;
import pe.com.lista_tareas.Services.UserService;
import pe.com.lista_tareas.entities.Client;
import pe.com.lista_tareas.entities.User;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Client> listAll() {
        List<Client> clients = clientRepository.findAll();
        for(Client client : clients){
            client.setTasks(null);
        }
        return clients;
    }

    @Override
    public Client findById(Long id) {
        Client clientFound = clientRepository.findById(id).orElse(null);
        if(clientFound == null){
            throw new ResourceNotFoundException("No se encontró el cliente con el id: " + String.valueOf(id));
        }
        return clientFound;
    }

    @Override
    public Client findByUsername(String username) {
        Client clientFound = clientRepository.findClientByUsername(username);
        if(clientFound == null){
            throw new ResourceNotFoundException("No se encontró un cliente con el usuario: " + username);
        }
        return clientFound;
    }

    @Override
    @Transactional
    public Client save(DTOUser dtoUser) {
        User user = new User();
        user.setUsername(dtoUser.getUsername());
        user.setPassword(dtoUser.getPassword());
        user = userService.register(dtoUser);

        Client client = new Client();
        client.setUsername(dtoUser.getUsername());
        client.setPassword(dtoUser.getPassword());
        client.setUser(user);
        return clientRepository.save(client);
    }

    @Override
    public Client save(Client client) {
        if(client.getName() == null ||client.getName().isEmpty() || client.getEmail() == null || client.getEmail().isEmpty()){
            throw new IncompleteDataException("Ingrese todos los datos");
        }

        DTOUser dtoUser = new DTOUser(null, client.getUsername(), client.getPassword(), "client");

        User existingUser = userService.register(dtoUser);

        client.setUser(existingUser);
        return clientRepository.save(client);
    }
}
