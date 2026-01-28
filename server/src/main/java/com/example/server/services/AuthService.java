package com.example.server.services;

import org.springframework.http.ResponseEntity;

import com.example.server.payload.LoginRequest;
import com.example.server.payload.RegisterRequest;

import jakarta.transaction.Transactional;


public interface AuthService {

    ResponseEntity<?> registerNewUser(RegisterRequest registerRequest);

    ResponseEntity<?> loginService(LoginRequest loginRequest);

    @Transactional
    ResponseEntity<?> forgetPasswordService(String email);
    
}
