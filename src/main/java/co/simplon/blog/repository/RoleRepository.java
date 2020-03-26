package co.simplon.blog.repository;

import co.simplon.blog.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleRepository extends CrudRepository<Role, Long> {
}
