package co.simplon.blog.repository;

import co.simplon.blog.model.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Josselin Tobelem
 */
public interface PostRepository extends CrudRepository<Post, Long> {
}
