package co.simplon.blog.service;

import co.simplon.blog.jwt.JwtTokenProvider;
import co.simplon.blog.model.Role;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SignServiceTests {

    @Autowired
    SignService signService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @MockBean
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private String usr = "admin";
    private String pwd = "admin";

    @Test
    public void signinUserNotFound() {
        User user = new User(usr, passwordEncoder.encode(pwd), new Role("TEST"));

        when(this.userRepository.findByName(usr)).thenReturn(Optional.of(user));

        assertThrows(BadCredentialsException.class, () -> this.signService.signin("toto", pwd));
    }

    @Test
    public void signinPwdKo() {
        User user = new User(usr, passwordEncoder.encode(pwd), new Role("TEST"));

        when(this.userRepository.findByName(usr)).thenReturn(Optional.of(user));

        assertThrows(BadCredentialsException.class, () -> this.signService.signin(usr, "bad pwd"));
    }

    @Test
    public void signinOk() {
        User user = new User(usr, passwordEncoder.encode(pwd), new Role("TEST"));

        when(this.userRepository.findByName(usr)).thenReturn(Optional.of(user));

        String token = this.signService.signin(usr, pwd);
        assertEquals(usr, this.jwtTokenProvider.getUsername(token));
    }
}
