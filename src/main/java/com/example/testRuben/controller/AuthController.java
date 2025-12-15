package com.example.testRuben.controller;

import com.example.testRuben.model.ResetEmailDTO;
import com.example.testRuben.model.UserModel;
import com.example.testRuben.service.AuthService;
import com.example.testRuben.service.JwtService;
import com.example.testRuben.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    private JwtService jwtService;

    @GetMapping("/")
    public String home() {
        return "Bienvenido. <a href='/oauth2/authorization/google'>Login con Google</a>";
    }

    @GetMapping("/success")
    public String success(@AuthenticationPrincipal OAuth2User user) {
        return "Hola " + user.getAttribute("name") +
                "<br>Email: " + user.getAttribute("email") +
                "<br><img src='" + user.getAttribute("picture") + "' width='100'/>";
    }

    @PutMapping("/reset-email")
    public String resetEmail(@RequestBody ResetEmailDTO requestBody, HttpServletRequest request) {
        String emailFromToken = (String) request.getAttribute("email");
        return authService.resetEmail(emailFromToken, requestBody.getEmail());
    }

    @GetMapping("/verify-token")
    public Map<String, String> verifyToken(HttpServletRequest request) {
        String email = (String) request.getAttribute("email");
        String role = (String) request.getAttribute("role");
        return Map.of("email", email, "role", role);
    }
}
