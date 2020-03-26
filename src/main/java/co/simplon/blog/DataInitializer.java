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
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Cette classe est chargée au lancement de l'application. Elle sert à
 * enregistrer des données en base.
 * 
 * @author Josselin Tobelem
 *
 */
@Component
public class DataInitializer implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

	private PostRepository postRepository;
	private RoleRepository roleRepository;
	private UserRepository userRepository;

	public DataInitializer(final PostRepository postRepository, final RoleRepository roleRepository,
                           final UserRepository userRepository) {
		this.postRepository = postRepository;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}

	public void initData() {

		try {
			Role userRole = new Role("USER");
			Role adminRole = new Role("ADMIN");

			User jojo = new User("jojo23457","jtobelem@simplon.co", userRole);
			User capo = new User("jtobelem-simplon","capo", adminRole);

			Post post1 = new Post("Début", "Au premier jour il n'y avait rien", LocalDateTime.of(0,1,1,0,0,0), jojo);
			Post post2 = new Post("Encore", "Puis quelqu'un dit hello", LocalDateTime.now(), jojo);
			Post post3 = new Post("Hello", "Hello Jojo!", LocalDateTime.now(), capo);


			if (!roleRepository.findAll().iterator().hasNext()) {
				roleRepository.saveAll(Arrays.asList(userRole, adminRole));
			}
			if (!userRepository.findAll().iterator().hasNext()) {
				userRepository.saveAll(Arrays.asList(jojo, capo));
			}
			if (!postRepository.findAll().iterator().hasNext()) {
				postRepository.saveAll(Arrays.asList(post1, post2, post3));
			}


		} catch (final Exception ex) {
			logger.error("Exception while inserting mock data {}", ex);
		}

	}

	public void logCreated(){
		roleRepository.findAll().forEach(elem -> logger.info(elem.toString()));
		userRepository.findAll().forEach(elem -> logger.info(elem.toString()));
		postRepository.findAll().forEach(elem -> logger.info(elem.toString()));
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("******** Initializing Data ***********");
		initData();
		logCreated();
		logger.info("******** Data initialized ***********");
	}
}
