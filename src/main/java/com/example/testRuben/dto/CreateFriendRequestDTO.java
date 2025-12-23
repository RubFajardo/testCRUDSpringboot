package com.example.testRuben.dto;

public class CreateFriendRequestDTO {

    private Long receiverId;

    public CreateFriendRequestDTO() {}

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long id) {
        this.receiverId = id;
    }
}
