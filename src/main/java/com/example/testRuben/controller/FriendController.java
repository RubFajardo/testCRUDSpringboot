package com.example.testRuben.controller;

import com.example.testRuben.model.FriendRequest;
import com.example.testRuben.model.UserModel;
import com.example.testRuben.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/request/create/{receiverId}")
    public String createRequest(@PathVariable Long receiverId, HttpServletRequest request) {
        Long idFromToken = (Long) request.getAttribute("id");
        return friendService.createRequest(idFromToken, receiverId);
    }

    @PutMapping("/request/{requestId}/accept")
    public String acceptFriend (@PathVariable Long requestId, HttpServletRequest request) {
        Long idFromToken = (Long) request.getAttribute("id");
        return friendService.acceptFriend(requestId, idFromToken);
    }

    @PutMapping("/request/{requestId}/reject")
    public String rejectFriend (@PathVariable Long requestId, HttpServletRequest request) {
        Long idFromToken = (Long) request.getAttribute("id");
        return friendService.rejectFriend(requestId, idFromToken);
    }

    @GetMapping("/request/pending")
    public List<FriendRequest> getPendingRequests (HttpServletRequest request) {
        Long idFromToken = (Long) request.getAttribute("id");
        return friendService.getPendingRequests(idFromToken);
    }

    @GetMapping("/getFriends")
    public List<UserModel> getFriends (HttpServletRequest request) {
        Long idFromToken = (Long) request.getAttribute("id");
        return friendService.getFriends(idFromToken);
    }

    @PostMapping("/block-user/{receiverId}")
    public String blockUser (@PathVariable Long receiverId, HttpServletRequest request) {
        Long idFromToken = (Long) request.getAttribute("id");
        return friendService.blockUser(idFromToken, receiverId);
    }

    @DeleteMapping("/unblock-user/{receiverId}")
    public String unBlockUser (@PathVariable Long receiverId, HttpServletRequest request) {
        Long idFromToken = (Long) request.getAttribute("id");
        return friendService.unblockUser(idFromToken, receiverId);
    }

    @DeleteMapping("/unfriend/{requestId}")
    public String unfriendUser (@PathVariable Long requestId, HttpServletRequest request) {
        Long idFromToken = (Long) request.getAttribute("id");
        return friendService.unfriendUser(requestId, idFromToken);
    }
}
