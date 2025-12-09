package com.example.testRuben.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

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
}
