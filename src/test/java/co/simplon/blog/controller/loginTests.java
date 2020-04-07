package co.simplon.blog.controller;

import co.simplon.blog.model.Role;
import co.simplon.blog.model.User;
import co.simplon.blog.service.SignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class loginTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignService signService;

    //////////////////////////////////////////////////// variables pour les tests

    private String token = "eyJhbGciOiJIUzI1NiJ9" +
            ".eyJzdWIiOiJjYXBvIiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiQURNSU4ifV0sImlhdCI6MTU4NTcyNTg3OCwiZXhwIjoxNTg1NzI5NDc4fQ" +
            ".U6l-e5n3uuIYrkvy1SiekRwstQLpCWeXlb4jxCm1Dwk";

    private String usr = "admin";
    private String pwd = "admin";

    //////////////////////////////////////////////////// pour générer un json à partir d'instances

    @Autowired
    private ObjectMapper objectMapper;

    private JacksonTester<User> userJacksonTester;

    @BeforeEach
    public void setUp() {
        JacksonTester.initFields(this, objectMapper);
    }

    //////////////////////////////////////////////////// fin pour générer un json à partir d'instances


    @Test
    public void signinBadCredentials() throws Exception {
        // Configuration du mockService
        when(this.signService.signin(usr, pwd)).thenReturn(token);
        when(this.signService.signin("toto", "toto")).thenThrow(BadCredentialsException.class);

        // initialisation des paramètres à envoyer
        User user = new User("toto", "toto", new Role("TEST"));
        String jsonContent = userJacksonTester.write(user).getJson();

        // execution de la requete
        ResultActions result = mockMvc.perform(post("/api/sign-in").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonContent));
        ;

        // test du résultat
        result.andExpect(status().isForbidden());

    }

    @Test
    public void signinPwdOk() throws Exception {
        // Configuration du mockService
        when(this.signService.signin(usr, pwd)).thenReturn(token);

        // initialisation des paramètres à envoyer
        User user = new User(usr, pwd, new Role("TEST"));
        String jsonContent = userJacksonTester.write(user).getJson();

        // execution de la requete
        ResultActions result = mockMvc.perform(post("/api/sign-in").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonContent));
        ;

        // test du résultat
        result.andExpect(status().isOk())
                .andExpect(jsonPath("access_token").value(token));
    }

}
