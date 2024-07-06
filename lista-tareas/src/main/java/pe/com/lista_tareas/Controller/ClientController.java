package pe.com.lista_tareas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.lista_tareas.Services.ClientService;
import pe.com.lista_tareas.Services.UserService;
import pe.com.lista_tareas.entities.Client;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    UserService userService;

    @GetMapping("/client/{username}")
    public ResponseEntity<Client> getUserByUsername(@PathVariable("username") String username) {
        Client client = clientService.findByUsername(username);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @GetMapping("/client/id/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable("id") Long id) {
        Client client = clientService.findById(id);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PostMapping("/client")
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client newClient = clientService.save(client);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    @PutMapping("/client/update")
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        Client foundClient = clientService.findById(client.getId());

        if(client.getName() != null) {
            foundClient.setName(client.getName());
        }

        if(client.getLastName() != null) {
            foundClient.setLastName(client.getLastName());
        }

        if(client.getEmail() != null) {
            foundClient.setEmail(client.getEmail());
        }

        if(client.getPhoneNumber() != null){
            foundClient.setPhoneNumber(client.getPhoneNumber());
        }

        Client newClient = clientService.save(foundClient);
        return new ResponseEntity<>(newClient, HttpStatus.OK);
    }
}
