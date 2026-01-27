package com.example.server.services;

import org.springframework.http.ResponseEntity;

import com.example.server.payload.LoginRequest;
import com.example.server.payload.RegisterRequest;

import jakarta.validation.Valid;

public interface AuthService {

    ResponseEntity<?> registerNewUser(RegisterRequest registerRequest);

    ResponseEntity<?> loginService(LoginRequest loginRequest);
    
}
