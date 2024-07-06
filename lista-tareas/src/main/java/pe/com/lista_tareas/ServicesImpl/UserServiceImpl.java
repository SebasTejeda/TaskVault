package pe.com.lista_tareas.ServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.com.lista_tareas.DTOs.DTOUser;
import pe.com.lista_tareas.Exceptions.ResourceNotFoundException;
import pe.com.lista_tareas.Repository.AuthorityRepository;
import pe.com.lista_tareas.Repository.UserRepository;
import pe.com.lista_tareas.Services.UserService;
import pe.com.lista_tareas.entities.AuthorityName;
import pe.com.lista_tareas.entities.User;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityRepository authorityRepository;

    @Override
    public User findById(Long id) {
        User userFound = userRepository.findById(id).orElse(null);
        if(userFound == null){
            throw new ResourceNotFoundException("User not found");
        }
        return userFound;
    }

    @Override
    public User findClientByUsername(String username) {
        User userFound  = userRepository.findClientByUsername(username);
        if(userFound == null){
            throw new ResourceNotFoundException("User not found");
        }
        return userFound;
    }

    @Override
    public User register(DTOUser dtoUser) {
        User user = new User();
        user.setUsername(dtoUser.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(dtoUser.getPassword()));
        if(Objects.equals(dtoUser.getType(), "client")){
            user.setAuthorities(List.of(
                    authorityRepository.findByAuthorityName(AuthorityName.ROLE_CLIENT)
            ));
        }
        else if(Objects.equals(dtoUser.getType(), "admin")){
            user.setAuthorities(List.of(
                    authorityRepository.findByAuthorityName(AuthorityName.ROLE_ADMIN)
            ));
        }
        return userRepository.save(user);
    }
}
