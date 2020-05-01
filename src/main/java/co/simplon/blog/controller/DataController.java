package co.simplon.blog.controller;

import co.simplon.blog.DataInitializer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api")
public class DataController {

    private final DataInitializer dataInitializer;

    @GetMapping("/init")
    public void initData() throws Exception {
        dataInitializer.run(); // TODO gérer les exception du dataIniti
    }
}
