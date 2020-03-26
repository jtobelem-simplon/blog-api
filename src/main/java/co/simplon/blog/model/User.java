package co.simplon.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String githubLogin;

    @JsonIgnore
    private String password;

    @ManyToOne
    private Role role;

    public User(String githubLogin, String name, Role role) {
        this.githubLogin = githubLogin;
        this.name = name;
        this.role = role;
    }
}
