package com.example.testRuben.controller;

import com.example.testRuben.model.ResetPasswordDTO;
import com.example.testRuben.model.UserModel;
import com.example.testRuben.repository.UserRepository;
import com.example.testRuben.service.AuthService;
import com.example.testRuben.service.JwtService;
import com.example.testRuben.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    @Autowired
    private AuthService authService;
    private JwtService jwtService;
    private UserRepository userRepository;

    //Routes

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

    @PostMapping("/register")
    public String register(@RequestBody UserModel request) {
        return authService.register(request.getEmail(), request.getPassword(), request.getRole());
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserModel request) {
        UserModel user = authService.login(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(user);
        return Map.of("token", token, "role", user.getRole());
    }

    @GetMapping("/auth/profile")
    public Map<String, String> profile(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        UserModel user = userRepository.findByEmail(email).orElseThrow();

        return Map.of(
                "email", user.getEmail(),
                "role", user.getRole()
        );
    }

    @PutMapping("/auth/reset-password")
    public String resetPassword (@RequestBody ResetPasswordDTO request) {
        return authService.resetPassword(request.getEmail(), request.getOldPassword(), request.getNewPassword());
    }

    
}