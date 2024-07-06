package pe.com.lista_tareas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pe.com.lista_tareas.DTOs.DTOToken;
import pe.com.lista_tareas.DTOs.DTOUser;
import pe.com.lista_tareas.Services.UserService;
import pe.com.lista_tareas.entities.User;
import pe.com.lista_tareas.security.JwtUtilService;
import pe.com.lista_tareas.security.SecurityUser;

import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtTokenUtil;

    @Autowired
    UserService userService;
    @Autowired
    private JwtUtilService jwtUtilService;

    @PostMapping("/users/register")
    public ResponseEntity<User> registerUser(@RequestBody DTOUser user) {
        User newUser = userService.register(user);
        return new ResponseEntity<User>(newUser,HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        User user = userService.findClientByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<DTOToken> authenticate(@RequestBody DTOUser user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(),
                        user.getPassword()));
        SecurityUser securityUser = (SecurityUser) this.userDetailsService.loadUserByUsername(
                user.getUsername());
        String jwt = jwtUtilService.generateToken(securityUser);
        Long id = securityUser.getUser().getId();

        String authoritiesString = securityUser.getUser().getAuthorities().stream()
                .map(n -> String.valueOf(n.getAuthorityName().toString()))
                .collect(Collectors.joining(";", "", ""));
        System.out.println(authoritiesString);
        return new ResponseEntity<>(new DTOToken(jwt, id, authoritiesString), HttpStatus.OK);
    }
}
