package co.simplon.blog.service;

import co.simplon.blog.exception.ExistingUsernameException;
import co.simplon.blog.jwt.JwtTokenProvider;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Josselin Tobelem
 */
@Service
public class SignService {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    public SignService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String signin(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Optional<User> user = userRepository.findByName(username);

        if (user.isPresent()) {
            return jwtTokenProvider.createToken(username, Arrays.asList(user.get().getRole()));
        } else {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
    }

    public String signup(User user) {
        if (!userRepository.findByName(user.getName()).isPresent()) {
            create(user);
            return jwtTokenProvider.createToken(user.getName(), Arrays.asList(user.getRole()));
        } else {
            throw new ExistingUsernameException("User '" + user.getName() + "' already registered");
        }
    }

    public User create(User user) {
        if (user.getPassword() == null) {
            user.setPassword(user.getName());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
