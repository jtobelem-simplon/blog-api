package co.simplon.blog.controller;

import co.simplon.blog.model.Post;
import co.simplon.blog.model.User;
import co.simplon.blog.repository.PostRepository;
import co.simplon.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;


@RestController
public class PostController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    @RequestMapping(path = "/home/posts")
    public @ResponseBody
    Iterable<Post> getAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); // TODO

        return postRepository.findAll();
    }

    @GetMapping("/error")
    @ResponseBody
    public String error(HttpServletRequest request) {

        String message = (String) request.getSession().getAttribute("error.message");
        request.getSession().removeAttribute("error.message");
        return message;
    }

    @PostMapping("/api/posts")
    public @ResponseBody Post addNew(@RequestBody Post newPost) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> user = userRepository.findByName(username);
        System.out.println(username);

        if (user.isPresent()) {
            newPost.setAuthor(user.get());
            newPost.setDateTime(LocalDateTime.now());
        }
        return postRepository.save(newPost);
    }
}


