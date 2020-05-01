package co.simplon.blog.controller;

import co.simplon.blog.DataInitializer;
import co.simplon.blog.exception.ExistingUsernameException;
import co.simplon.blog.model.Post;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.UserRepository;
import co.simplon.blog.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * @author Josselin Tobelem
 */
@RestController
@RequestMapping(path = "/api")
public class UserController {

    @Autowired
    private DataInitializer dataInitializer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SignService signService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/users")
    public @ResponseBody
    User create(@RequestBody User newUser) {
        return this.signService.create(newUser); // TODO cas d'erreur
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/users")
    public @ResponseBody
    User update(@RequestBody User newUser) {
        // TODO gerer le reinit de password
        return userRepository.save(newUser);
    }

    // TODO deplacer les méthodes d'authentif dans un controller a part
    /**
     * Method to register a new user in database.
     *
     * @param user the new user to create.
     * @return a JWT if sign up is ok, a bad response code otherwise.
     */
    @PostMapping("/sign-up")
    public Map signUp(@RequestBody User user) {
            String token = signService.signup(user);
        return Collections.singletonMap("access_token", token);
    }

    /**
     * Method to sign in a user (already existing).
     *
     * @param user the user to sign in to the app.
     * @return a JWT if sign in is ok, a bad response code otherwise.
     */
    @PostMapping("/sign-in")
    public @ResponseBody
    Map signIn(@RequestBody User user) {
        String token = signService.signin(user.getName(), user.getPassword());
        return Collections.singletonMap("access_token", token);
    }

    @GetMapping("/init")
    public void initData() throws Exception {
        dataInitializer.run(); // TODO gérer les exception du dataIniti
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
