package co.simplon.blog.controller;

import co.simplon.blog.DataInitializer;
import co.simplon.blog.exception.ExistingUsernameException;
import co.simplon.blog.model.Post;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.UserRepository;
import co.simplon.blog.service.SignService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * @author Josselin Tobelem
 */
@AllArgsConstructor
@RestController
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserRepository userRepository;

    private final SignService signService;

    @PostMapping
    public @ResponseBody
    User create(@RequestBody User newUser) {
        return this.signService.create(newUser); // TODO cas d'erreur
    }

    @PutMapping
    public @ResponseBody
    User update(@RequestBody User newUser) {
        // TODO gerer le reinit de password
        return userRepository.save(newUser);
    }

    @GetMapping
    public @ResponseBody
    Iterable<User> getAll() {
        return userRepository.findAll();
    } // TODO passer par le service

    @DeleteMapping
    public void delete(@PathVariable long userID) {
        userRepository.deleteById(userID);
    } // TODO passer par le service

}
