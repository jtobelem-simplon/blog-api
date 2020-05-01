package co.simplon.blog.repository;

import co.simplon.blog.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Josselin Tobelem
 */
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByName(String name);
}
