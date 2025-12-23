package com.example.testRuben.service;

import com.example.testRuben.model.FriendRequest;
import com.example.testRuben.model.FriendStatus;
import com.example.testRuben.model.UserModel;
import com.example.testRuben.repository.FriendRequestRepository;
import com.example.testRuben.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FriendService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public String createRequest(Long senderId, Long receiverId) {

        if (senderId.equals(receiverId)) {
            throw new RuntimeException("No puede enviar solicitud a sí mismo");
        }

        UserModel sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Usuario sender no encontrado"));

        UserModel receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Usuario receiver no encontrado"));

        Optional<FriendRequest> existing =
                friendRequestRepository.findBetweenUsers(sender, receiver);

        if (existing.isPresent()) {
            FriendRequest request = existing.get();

            switch (request.getStatus()) {
                case PENDING ->
                        throw new RuntimeException("Ya existe una solicitud pendiente");

                case ACCEPTED ->
                        throw new RuntimeException("Ya son amigos");

                case BLOCKED ->
                        throw new RuntimeException("Has sido bloqueado, no puedes enviar una solicitud de amistad");

                case REJECTED -> {
                    request.setSender(sender);
                    request.setReceiver(receiver);
                    request.setStatus(FriendStatus.PENDING);
                    request.setCreatedAt(LocalDateTime.now());
                    friendRequestRepository.save(request);
                    return "Solicitud de amistad enviada";
                }
            }
        }
        FriendRequest friendRequest = new FriendRequest(sender, receiver);
        friendRequestRepository.save(friendRequest);

        return "Solicitud de amistad enviada";
    }

    public String acceptFriend(Long requestId, Long currentUserId) {

        FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Petición no encontrada"));

        if (!request.getReceiver().getId().equals(currentUserId)) {
            throw new RuntimeException("No tienes permiso para aceptar esta solicitud");
        }

        if (request.getStatus().equals(FriendStatus.PENDING)) {
            request.setStatus(FriendStatus.ACCEPTED);
            friendRequestRepository.save(request);
            return "La solicitud ha sido aceptada";
        }
        return "Esta solicitud ya ha sido procesada";
    }

    public String rejectFriend(Long requestId, Long currentUserId) {

        FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Petición no encontrada"));

        if (!request.getReceiver().getId().equals(currentUserId)) {
            throw new RuntimeException("No tienes permiso para rechazar esta solicitud");
        }

        if (request.getStatus().equals(FriendStatus.PENDING)) {
            request.setStatus(FriendStatus.REJECTED);
            friendRequestRepository.save(request);
            return "La solicitud ha sido rechazada";
        }
        return "Esta solicitud ya ha sido procesada";
    }

    public List<FriendRequest> getPendingRequests (Long userId) {
        UserModel receiver = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario receiver no encontrado"));

        return friendRequestRepository.findByReceiverAndStatus(receiver, FriendStatus.PENDING);
    }

    public List<UserModel> getFriends (Long userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return friendRequestRepository.findAllFriendsByUser(user);
    }

    public String blockUser (Long senderId, Long receiverId) {

        if (senderId.equals(receiverId)) {
            throw new RuntimeException("No puedes bloquearte a ti mismo");
        }

        UserModel sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Usuario sender no encontrado"));

        UserModel receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Usuario receiver no encontrado"));


        Optional<FriendRequest> existing =
                friendRequestRepository.findBetweenUsers(sender, receiver);

        FriendRequest friendRequest;

        if (existing.isPresent()) {
            friendRequest = existing.get();

            if (friendRequest.getStatus().equals(FriendStatus.BLOCKED)) {
                return "El usuario ya está bloqueado";
            }

            friendRequest.setStatus(FriendStatus.BLOCKED);
        } else {

            friendRequest = new FriendRequest(sender, receiver);
            friendRequest.setStatus(FriendStatus.BLOCKED);
        }

        friendRequestRepository.save(friendRequest);
        return "Usuario bloqueado exitosamente";
    }

    public String unblockUser (Long requestId, Long currentUserId) {

        FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Petición no encontrada"));

        if (!request.getSender().getId().equals(currentUserId)) {
            throw new RuntimeException("No tienes permiso para desbloquear a este usuario");
        }

        if (!request.getStatus().equals(FriendStatus.BLOCKED)) {
            return "Este usuario no está bloqueado";
        }

        friendRequestRepository.delete(request);
        return "Usuario desbloqueado correctamente";
    }

    public String unfriendUser (Long requestId, Long currentUserId) {
        FriendRequest request = friendRequestRepository.findById(requestId).orElseThrow(() -> new RuntimeException("Relación no encontrada"));

        if (!request.getStatus().equals(FriendStatus.ACCEPTED)) {
            return "No son amigos, no puedes eliminar la amistad";
        }

        Long senderId = request.getSender().getId();
        Long receiverId = request.getReceiver().getId();

        if (!senderId.equals(currentUserId) && !receiverId.equals(currentUserId)) {
            throw new RuntimeException("No tienes permiso para eliminar esta amistad");
        }

        friendRequestRepository.delete(request);
        return "Amistad eliminada";
    }
}


