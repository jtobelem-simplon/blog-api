package co.simplon.blog.controller;

import co.simplon.blog.DataInitializer;
import co.simplon.blog.model.Role;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.UserRepository;
import co.simplon.blog.service.MyUserDetailService;
import co.simplon.blog.service.SignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = { loginTests.class })
@SpringBootTest
@AutoConfigureMockMvc
public class loginTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SignService signService;

    @MockBean
    private DataInitializer dataInitializer;

    @MockBean
    private UserRepository userRepository;


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

    @BeforeEach
    public void setup() {
        when(this.signService.signin(usr, pwd)).thenReturn(token);
    }

    @Test
    public void signinBadCredentials() throws Exception {
        // Configuration du mockService

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

    @Test
    public void testInit() throws Exception {
//        when(this.dataInitializer.run()).then()
        ResultActions result = mockMvc.perform(get("/api/init"));

        // test du résultat
        result.andExpect(status().isOk());
    }



    //    https://stackoverflow.com/questions/5403818/how-to-junit-tests-a-preauthorize-annotation-and-its-spring-el-specified-by-a-s
    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testGetAllAuthOk() throws Exception {
        when(this.userRepository.findAll()).thenReturn(new HashSet());
        ResultActions result = mockMvc.perform(get("/api/users"));

        // test du résultat
        result.andExpect(status().isOk());
    }

    @Test
    public void testGetAllAuthNotOk() throws Exception {


        ResultActions result = mockMvc.perform(get("/api/users"));

        // test du résultat
        result.andExpect(status().isForbidden());

    }

}
