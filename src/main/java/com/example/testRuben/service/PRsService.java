package com.example.testRuben.service;

import com.example.testRuben.model.PRModel;
import com.example.testRuben.model.UserModel;
import com.example.testRuben.repository.PRsRepository;
import com.example.testRuben.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PRsService {

    @Autowired
    private PRsRepository prsRepository;

    @Autowired
    private UserRepository userRepository;

    public PRModel savePR(PRModel pr, Long currentUserId) {

        UserModel user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Optional<PRModel> existingPR =
                prsRepository.findByUserIdAndWorkoutType(currentUserId, pr.getWorkoutType());

        if (existingPR.isPresent()) {
            PRModel storedPR = existingPR.get();

            if (pr.getWeightLifted() <= storedPR.getWeightLifted()) {
                throw new RuntimeException(
                        "Ya tienes un PR mayor o igual para este ejercicio"
                );
            }

            storedPR.setWeightLifted(pr.getWeightLifted());
            storedPR.setCreatedAt(LocalDateTime.now());

            return prsRepository.save(storedPR);
        }

        PRModel newPR = new PRModel(pr.getWorkoutType(), pr.getWeightLifted());
        newPR.setUser(user);

        return prsRepository.save(newPR);
    }


    public List<PRModel> listUserPRs(Long userId) {
        return prsRepository.findByUserId(userId);
    }

    public String deletePR(Long currentUserId, Long prId) {

        PRModel pr = prsRepository.findById(prId)
                .orElseThrow(() -> new RuntimeException("PR no encontrado"));

        if (!pr.getUser().getId().equals(currentUserId)) {
            throw new RuntimeException("No tienes permiso para eliminar este PR");
        }

        prsRepository.delete(pr);
        return "PR eliminado correctamente";
    }

}