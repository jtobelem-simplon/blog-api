package co.simplon.blog.controller;

import co.simplon.blog.exception.ExistingUsernameException;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.UserRepository;
import co.simplon.blog.security.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SignService signService;

    /**
     * Method to register a new user in database.
     * @param user the new user to create.
     * @return a JWT if sign up is ok, a bad response code otherwise.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody User user) {
        try {
            return ResponseEntity.ok(signService.signup(user));
        } catch (ExistingUsernameException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Method to sign in a user (already existing).
     * @param user the user to sign in to the app.
     * @return a JWT if sign in is ok, a bad response code otherwise.
     */
    @PostMapping("/sign-in")
    public @ResponseBody ResponseEntity<Map> signIn(@RequestBody User user) {
        try {
            String token = signService.signin(user.getName(), user.getPassword());
            return ResponseEntity.ok(Collections.singletonMap("access_token",token));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')") // TODO ne pas écrire cette méthode
    @GetMapping("/users")
    public @ResponseBody
    Iterable<User> getAll() {
        return userRepository.findAll();
    }


    @PreAuthorize("hasAuthority('ADMIN')") // TODO ne pas écrire cette méthode
    @DeleteMapping("/users")
    public void delete(@PathVariable long userID) {
        userRepository.deleteById(userID);
    }


}
