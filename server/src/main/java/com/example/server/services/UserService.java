package com.example.server.services;

import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> getUsers();

    
} 