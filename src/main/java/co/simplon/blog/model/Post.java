package co.simplon.blog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Josselin Tobelem
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String content;
    private LocalDateTime dateTime;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    public Post(String title, String content, LocalDateTime dateTime, User author) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.author = author;
    }

}
