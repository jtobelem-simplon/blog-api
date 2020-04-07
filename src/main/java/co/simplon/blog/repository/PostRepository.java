package co.simplon.blog.repository;

import co.simplon.blog.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author Josselin Tobelem
 */
@RepositoryRestResource
public interface PostRepository extends CrudRepository<Post, Long> {
}
