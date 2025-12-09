package com.example.testRuben.repository;

import com.example.testRuben.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    List<UserModel> findByName(String name);
    Optional<UserModel> findByEmail(String email);
}