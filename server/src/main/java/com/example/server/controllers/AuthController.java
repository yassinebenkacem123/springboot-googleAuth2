package com.example.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.payload.LoginRequest;
import com.example.server.payload.RegisterRequest;
import com.example.server.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    // login :
    @PostMapping("/login")
    public ResponseEntity<?> loginMethod(@RequestBody @Valid LoginRequest loginRequest){
        return authService.loginService(loginRequest);
    }

    // register :
    @PostMapping("/register")
    public ResponseEntity<?> registerMethod(@RequestBody @Valid RegisterRequest registerRequest){
        return authService.registerNewUser(registerRequest);

    }

    // forget password :
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestParam(name="email") String email){
        return authService.forgetPasswordService(email);
    }
    // reset password : 
    
}
