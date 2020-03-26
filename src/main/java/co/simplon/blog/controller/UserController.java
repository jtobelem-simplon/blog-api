package co.simplon.blog.controller;

import co.simplon.blog.model.User;
import co.simplon.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;


//    @PreAuthorize("hasAuthority('Admin')") TODO
@PreAuthorize("hasAuthority('admin')")
@GetMapping
    @RequestMapping(path = "/admin/users")
    public @ResponseBody
    Iterable<User> getAll() {
        return userRepository.findAll();
    }
}
