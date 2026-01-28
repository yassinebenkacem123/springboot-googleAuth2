package com.example.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.models.PasswordResetToken;
import com.example.server.models.User;

@Repository
public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

    void deleteByUser(User user);

    PasswordResetToken findByUser(User user);
    
}
