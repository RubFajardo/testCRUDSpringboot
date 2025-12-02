package com.example.testRuben.controller;

import com.example.testRuben.model.UserModel;
import com.example.testRuben.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String user1() {
        return userService.saludoBasico();
    }

    @GetMapping("/user/all")
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }


    @GetMapping("/user/byName/{nombre}")
    public List<UserModel> getUsersbyName(@PathVariable String nombre) {
        return userService.getUsersByName(nombre);
    }

    @PostMapping("/create")
    public UserModel createUser(@RequestBody UserModel request) {
        return userService.crearUsuario(request);
    }

    @GetMapping("/{id}")
    public UserModel getById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/ageCheck")
    public String ageCheck(@RequestBody UserModel request) {
        return userService.esMayorDeEdad(request);
    }

    @PostMapping("/yearBorn")
    public String yearBorn(@RequestBody UserModel request) {
        return userService.dayBorn(request);
    }

}