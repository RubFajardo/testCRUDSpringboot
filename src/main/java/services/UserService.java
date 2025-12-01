package services;

import models.userModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public userModel getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<userModel> getAllUsers() {
        return userRepository.findAll();
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User eliminado";
    }

    public List<userModel> getUsersByName(String name){
        return userRepository.findByName(name);
    }

    public String saludoBasico() {
        return "Hola user desde el servicio!";
    }

    public String saludoConNombre(String nombre) {
        return "Hola " + nombre + " desde el servicio!";
    }

    public userModel crearUsuario(userModel user) {
        return userRepository.save(user);
    }

    public userModel obtenerUsuario() {
        return new userModel("Carlos", 23);
    }

    public String esMayorDeEdad(userModel user) {
        if (user.getAge() >= 18) {
            return "Mayor de edad";
        }
        return "Menor de edad";
    }

    public String dayBorn(userModel user) {
        int yearBorn = 2025 - user.getAge();
        return "Naciste en el año " + yearBorn;
    }
}
