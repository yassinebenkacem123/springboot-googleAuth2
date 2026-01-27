package com.example.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.payload.LoginRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> loginMethod(@RequestBody @Valid LoginRequest loginRequest){
        return null;
    }
    
}
