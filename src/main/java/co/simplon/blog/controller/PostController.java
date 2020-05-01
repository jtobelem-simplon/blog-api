package co.simplon.blog.controller;

import co.simplon.blog.model.Post;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.PostRepository;
import co.simplon.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Josselin Tobelem
 */
@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/posts")
public class PostController {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @PutMapping
    public @ResponseBody
    Post update(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping
    public @ResponseBody
    Iterable<Post> getAll() {
        return postRepository.findAll();
    }

    @PostMapping
    public @ResponseBody
    Post addNew(@RequestBody Post newPost) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); // TODO déplacer ceci dans un service

        Optional<User> user = userRepository.findByName(username);

        if (user.isPresent()) {
            newPost.setAuthor(user.get());
            newPost.setDateTime(LocalDateTime.now());
        }
        return postRepository.save(newPost);
    }


}


