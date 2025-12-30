package com.example.testRuben.repository;

import com.example.testRuben.model.PRModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PRsRepository extends JpaRepository<PRModel, Long> {
    @Query("SELECT p FROM PRModel p WHERE p.user.id = :userId")
    List<PRModel> findByUserId(@Param("userId") Long userId);
    Optional<PRModel> findByUserIdAndWorkoutType(Long userId, String workoutType);
}