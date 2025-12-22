package com.example.testRuben.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "friendRequests")
public class FriendRequest {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserModel sender;

    @ManyToOne
    private UserModel receiver;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    private LocalDateTime createdAt;

    public FriendRequest() {}

    public FriendRequest(UserModel sender, UserModel receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = FriendStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    //setters y getters

    public UserModel getReceiver() {
        return receiver;
    }

    public void setReceiver(UserModel receiver) {
        this.receiver = receiver;
    }

    public UserModel getSender() {
        return sender;
    }

    public void setSender(UserModel sender) {
        this.sender = sender;
    }

    public LocalDateTime getCreatedAt() {return createdAt;}

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }
}
