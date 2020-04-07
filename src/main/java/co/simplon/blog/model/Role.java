package co.simplon.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author Josselin Tobelem
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 10)
    private String label;

    public Role(String label) {
        this.label = label;
    }

    @Override
    public String getAuthority() {
        return label;
    }
}
