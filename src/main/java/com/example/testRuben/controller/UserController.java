package com.example.testRuben.controller;

import com.example.testRuben.dto.ResetPasswordDTO;
import com.example.testRuben.model.UserModel;
import com.example.testRuben.repository.UserRepository;
import com.example.testRuben.service.AuthService;
import com.example.testRuben.service.JwtService;
import com.example.testRuben.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @GetMapping("/oauth2")
    public String home() {
        return "Bienvenido. <a href='/oauth2/authorization/google'>Login con Google</a>";
    }

    @GetMapping("/success")
    public String success(@AuthenticationPrincipal OAuth2User user) {
        return "Hola " + user.getAttribute("name") +
                "<br>Email: " + user.getAttribute("email") +
                "<br><img src='" + user.getAttribute("picture") + "' width='100'/>";
    }

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