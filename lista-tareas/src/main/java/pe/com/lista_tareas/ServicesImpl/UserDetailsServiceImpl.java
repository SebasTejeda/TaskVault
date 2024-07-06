package pe.com.lista_tareas.ServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.com.lista_tareas.Repository.UserRepository;
import pe.com.lista_tareas.entities.User;
import pe.com.lista_tareas.security.SecurityUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return new SecurityUser(user);
        }
        throw new UsernameNotFoundException("User not found "+username);
    }
}