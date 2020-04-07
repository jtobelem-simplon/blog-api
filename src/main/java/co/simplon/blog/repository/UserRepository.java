package co.simplon.blog.repository;

import co.simplon.blog.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * @author Josselin Tobelem
 */
@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String name);
}
