package pe.com.lista_tareas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="authorities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuthorityName authorityName;

    @JsonIgnore
    @ManyToMany(mappedBy = "authorities")
    private List<User> users;

    public Authority(AuthorityName authorityName) {this.authorityName = authorityName;}
}
