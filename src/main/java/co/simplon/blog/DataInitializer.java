package co.simplon.blog;

import co.simplon.blog.model.Post;
import co.simplon.blog.model.Role;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.PostRepository;
import co.simplon.blog.repository.RoleRepository;
import co.simplon.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

/**
 * Cette classe est chargée au lancement de l'application. Elle sert à
 * enregistrer des données en base.
 *
 * @author Josselin Tobelem
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private PostRepository postRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;


    public DataInitializer(final PostRepository postRepository, final RoleRepository roleRepository,
                           final UserRepository userRepository, final BCryptPasswordEncoder passwordEncoder
    ) {
        this.postRepository = postRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void initData() {

        try {
            Role userRole = new Role("USER");
            Role adminRole = new Role("ADMIN");

            User lp4 = new User("lp4", passwordEncoder.encode("lp4"), userRole);
            lp4.setBirthday(LocalDate.of(2019, 10,1));
            lp4.setEmail("lp4@simplon.co");

            User admin = new User("admin", passwordEncoder.encode("admin"), adminRole);
            admin.setBirthday(LocalDate.of(2020, 1,1));
            admin.setEmail("admin@simplon.co");

            User starJS = new User("starJS", passwordEncoder.encode("ThisIsNotAPassword"), userRole);
            User sangoku = new User("sangoku", passwordEncoder.encode("ThisIsNotAStrongPassword"), adminRole);

            Post post1 = new Post("Début", "Au premier jour il n'y avait rien", LocalDateTime.of(0, 1, 1, 0, 0, 0), starJS);
            Post post2 = new Post("Encore", "Puis quelqu'un dit hello", LocalDateTime.now(), starJS);
            Post post3 = new Post("Hello", "Hello starJS!", LocalDateTime.now(), sangoku);

            if (!roleRepository.findAll().iterator().hasNext()) {
                roleRepository.saveAll(Arrays.asList(userRole, adminRole));
            }
            if (!userRepository.findAll().iterator().hasNext()) {
                userRepository.saveAll(Arrays.asList(starJS, sangoku, lp4, admin));
            }
            if (!postRepository.findAll().iterator().hasNext()) {
                postRepository.saveAll(Arrays.asList(post1, post2, post3));
            }


        } catch (final Exception ex) {
            logger.error("Exception while inserting mock data {0}", ex);
        }

    }

    private void deleteAll() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    public void logCreated() {
        roleRepository.findAll().forEach(elem -> logger.info(elem.toString()));
        userRepository.findAll().forEach(elem -> logger.info(elem.toString()));
        postRepository.findAll().forEach(elem -> logger.info(elem.toString()));
    }

    @Override
    public void run(String... args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        logger.info("******** Initializing Data at {} ***********", LocalDateTime.now().format(formatter));
        deleteAll();
        initData();
        logCreated();
        logger.info("******** Data initialized at {} ***********", LocalDateTime.now().format(formatter));
    }
}
