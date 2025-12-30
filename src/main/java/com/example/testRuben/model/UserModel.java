package com.example.testRuben.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String role;

    public UserModel() {}

    public UserModel(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @OneToOne(mappedBy = "user")
    private StreetModel street;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<FriendRequest> sentRequests;

    @OneToMany(mappedBy = "receiver")
    private List<FriendRequest> receivedRequests;

    @OneToMany(mappedBy = "user")
    private List<PRModel> PRs = new ArrayList<>();

    // Getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) { this.role = role; }
}