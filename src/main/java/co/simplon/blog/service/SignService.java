package co.simplon.blog.service;

import co.simplon.blog.exception.ExistingUsernameException;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.UserRepository;
import co.simplon.blog.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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
        return jwtTokenProvider.createToken(username, Arrays.asList(userRepository.findByName(username).get().getRole()));
    }

    public String signup(User user) throws ExistingUsernameException {
        if (!userRepository.findByName(user.getName()).isPresent()) {
            User userToSave = new User(user.getName(), passwordEncoder.encode(user.getPassword()), user.getRole());
            userRepository.save(userToSave);
            return jwtTokenProvider.createToken(user.getName(), Arrays.asList(user.getRole()));
        } else {
            throw new ExistingUsernameException();
        }
    }
}
