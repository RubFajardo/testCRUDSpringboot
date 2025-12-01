package controller;

import models.userModel;
import org.springframework.web.bind.annotation.*;
import services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class user {

    private final UserService userService;

    public user(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String user1() {
        return userService.saludoBasico();
    }

    @GetMapping("/user/all")
    public List<userModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }


    @GetMapping("/user/byName/{nombre}")
    public List<userModel> getUsersbyName(@PathVariable String nombre) {
        return userService.getUsersByName(nombre);
    }

    @PostMapping("/create")
    public userModel createUser(@RequestBody userModel request) {
        return userService.crearUsuario(request);
    }

    @GetMapping("/{id}")
    public userModel getById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/ageCheck")
    public String ageCheck(@RequestBody userModel request) {
        return userService.esMayorDeEdad(request);
    }

    @PostMapping("/yearBorn")
    public String yearBorn(@RequestBody userModel request) {
        return userService.dayBorn(request);
    }
}