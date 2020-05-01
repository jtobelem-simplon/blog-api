package co.simplon.blog.controller;

import co.simplon.blog.model.Role;
import co.simplon.blog.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Josselin Tobelem
 */
@RestController
@RequestMapping(path = "/api/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @GetMapping
    public @ResponseBody
    Iterable<Role> getAll() {
        return roleRepository.findAll();
    }

}
