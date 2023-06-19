package org.example.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }

    @PostMapping("/greet/{name}")
    public String greetPerson(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    @PostMapping("/add")
    public int addNumbers(@RequestParam int a, @RequestParam int b) {
        return a + b;
    }
}
