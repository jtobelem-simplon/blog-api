package co.simplon.blog.controller;

import co.simplon.blog.model.Post;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.PostRepository;
import co.simplon.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // TODO ne pas avoir à écrire ce endpoint et garder par defaut celui du restRepo
    @GetMapping
    public @ResponseBody
    Iterable<Post> getAll() {
        return postRepository.findAll();
    }

    // TODO utiliser un handle ajout user pour ne pas avoir à écrire ce endpoint et garder par defaut celui du restRepo
    @PostMapping
    public @ResponseBody Post addNew(@RequestBody Post newPost) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> user = userRepository.findByName(username);

        if (user.isPresent()) {
            newPost.setAuthor(user.get());
            newPost.setDateTime(LocalDateTime.now());
        }
        return postRepository.save(newPost);
    }
}


