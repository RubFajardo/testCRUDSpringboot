package com.example.testRuben.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prs",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "workoutType"}
        )
)
public class PRModel {

    @Id
    @GeneratedValue
    private Long id;

    private String workoutType;
    private int weightLifted;
    private LocalDateTime createdAt;

    public PRModel () {}

    public PRModel (String workoutType, int weightLifted) {
        this.weightLifted = weightLifted;
        this.workoutType = workoutType;
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    public String getWorkoutType() { return workoutType;}
    public void setWorkoutType(String workoutType) { this.workoutType = workoutType;}

    public int getWeightLifted() {return weightLifted;}
    public void setWeightLifted(int weightLifted) {this.weightLifted = weightLifted;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

}
