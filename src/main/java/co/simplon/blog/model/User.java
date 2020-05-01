package co.simplon.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Josselin Tobelem
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // TODO commenté pour le junit ...
    private String password;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE) // TODO utilisé pour les tests, à vérifier
    private Role role;

    private String email;

    private LocalDate birthday;

    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
