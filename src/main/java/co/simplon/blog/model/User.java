package co.simplon.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // TODO commenté pour le junit ...
    private String password;

    @ManyToOne
    private Role role;

    public User(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
