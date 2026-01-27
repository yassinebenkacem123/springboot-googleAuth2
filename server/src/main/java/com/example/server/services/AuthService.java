package com.example.server.services;

import org.springframework.http.ResponseEntity;

import com.example.server.payload.LoginRequest;
import com.example.server.payload.RegisterRequest;


public interface AuthService {

    ResponseEntity<?> registerNewUser(RegisterRequest registerRequest);

    ResponseEntity<?> loginService(LoginRequest loginRequest);

    ResponseEntity<?> forgetPasswordService(String email);
    
}
