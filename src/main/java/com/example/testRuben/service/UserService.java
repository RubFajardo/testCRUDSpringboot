package com.example.testRuben.service;


import com.example.testRuben.model.UserModel;
import com.example.testRuben.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserModel getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User eliminado";
    }

    public String saludoBasico() {
        return "Hola user desde el servicio!";
    }

    public String saludoConNombre(String nombre) {
        return "Hola " + nombre + " desde el servicio!";
    }

    public UserModel crearUsuario(UserModel user) {
        return userRepository.save(user);
    }
}