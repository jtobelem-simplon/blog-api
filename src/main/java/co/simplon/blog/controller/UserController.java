package co.simplon.blog.controller;

import co.simplon.blog.exception.ExistingUsernameException;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.UserRepository;
import co.simplon.blog.security.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> signIn(@RequestBody User user) {
        try {
            return ResponseEntity.ok(signService.signin(user.getName(), user.getPassword()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    //    @PreAuthorize("hasAuthority('Admin')") TODO
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    public @ResponseBody
    Iterable<User> getAll() {
        return userRepository.findAll();
    }
}
