package com.example.testRuben.service;

import com.example.testRuben.model.UserModel;
import com.example.testRuben.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public UserModel login(String email, String password) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return user;
    }

    public String register(String email, String password, String role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email ya existe");
        }

        String hashedPassword = passwordEncoder.encode(password);

        userRepository.save(new UserModel(email, hashedPassword, role));
        return "Usuario registrado";
    }

    public String resetPassword (String email, String oldPass, String newPass) {
        UserModel user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw new RuntimeException("Contraseña vieja incorrecta");
        }
        String hashedPassword = passwordEncoder.encode(newPass);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return "Contraseña actualizada";
    }
}
