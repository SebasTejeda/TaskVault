package pe.com.lista_tareas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
/*
http://localhost:8080/swagger-ui/index.html
*/
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            // -- login
            "/api/login",
            "/api/users/register/**",
            "/api/users/**",
            // -- task
            "/api/task/**",
            // -- client
            "/api/client/**",
            "/api/client/id/**"
};
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                        authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(withDefaults());
        http.authorizeHttpRequests( (auth) ->auth
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        // user
                        .requestMatchers(HttpMethod.POST, "/api/users/register").hasAnyAuthority("ROLE_CLIENT")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyAuthority("ROLE_ADMIN")
                        // task
                        .requestMatchers(HttpMethod.POST, "/api/task/create").hasAnyAuthority("ROLE_CLIENT")
                        .requestMatchers(HttpMethod.GET, "/api/task/client/**").hasAnyAuthority("ROLE_CLIENT")
                        .requestMatchers(HttpMethod.GET, "/api/tasks").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/task/**").hasAnyAuthority("ROLE_CLIENT")
                        // client
                        .requestMatchers(HttpMethod.POST, "/api/client").hasAnyAuthority("ROLE_CLIENT")
                        .requestMatchers(HttpMethod.PUT, "/api/client/update").hasAnyAuthority("ROLE_CLIENT")
                        .anyRequest().authenticated()
        );
        http.sessionManagement( (session)-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}



