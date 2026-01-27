package com.example.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.models.User;


@Repository
public interface UserRepo extends JpaRepository<User, Long>{

    User findByUsername(String username);

    
} 