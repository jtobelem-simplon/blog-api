package co.simplon.blog.controller;

import co.simplon.blog.model.User;
import co.simplon.blog.service.SignService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api")
public class AuthenticationController {

    private final SignService signService;

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
}
