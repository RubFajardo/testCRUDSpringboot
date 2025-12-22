package com.example.testRuben.repository;

import com.example.testRuben.model.FriendRequest;
import com.example.testRuben.model.FriendStatus;
import com.example.testRuben.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository
        extends JpaRepository<FriendRequest, Long> {
    // Buscar entre dos usuarios una solicitud
    @Query("""
    SELECT fr FROM FriendRequest fr
    WHERE (fr.sender = :user1 AND fr.receiver = :user2)
       OR (fr.sender = :user2 AND fr.receiver = :user1)
""")
    Optional<FriendRequest> findBetweenUsers(
            @Param("user1") UserModel user1,
            @Param("user2") UserModel user2
    );

    // Listar todas las solicitudes pendientes a aceptar o rechazar por un usuario
    List<FriendRequest> findByReceiverAndStatus(UserModel receiver, FriendStatus status);

    // Obtener todos los amigos de un usuario
    @Query("SELECT CASE WHEN f.sender = :user THEN f.receiver ELSE f.sender END " +
            "FROM FriendRequest f WHERE (f.sender = :user OR f.receiver = :user) " +
            "AND f.status = 'ACCEPTED'")
    List<UserModel> findAllFriendsByUser(@Param("user") UserModel user);

    // Verificar si son amigos para ver perfil
    @Query("SELECT COUNT(f) > 0 FROM FriendRequest f WHERE " +
            "((f.sender = :u1 AND f.receiver = :u2) OR (f.sender = :u2 AND f.receiver = :u1)) " +
            "AND f.status = 'ACCEPTED'")
    boolean areFriends(@Param("u1") UserModel u1, @Param("u2") UserModel u2);
}
