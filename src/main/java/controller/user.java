package controller;

import models.userModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class user {
    @GetMapping("/user")
    public String user1() {
        return "Hola user desde Spring Boot!";
    }

    @GetMapping("/user/{nombre}")
    public String user2(@PathVariable String nombre) {
        return "Hola " + nombre + " desde Spring Boot!";
    }

    @PostMapping("/create")
    public String createUser(@RequestBody userModel request) {
        return "Usuario recibido: " + request.getName() + " (edad " + request.getAge() + ")";
    }

    @GetMapping("/userInfo")
    public userModel getUser() {
        return new userModel("Carlos", 23);
    }
}
