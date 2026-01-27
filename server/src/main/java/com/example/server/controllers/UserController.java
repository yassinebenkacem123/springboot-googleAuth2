package com.example.server.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.services.UserService;

@RestController
@RequestMapping("/api/v2")
public class UserController {

    @Autowired
    private UserService userService;
    //testing
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test(){
        Map<String,String> message = new HashMap<>();
        message.put("message","Server is running");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        return userService.getUsers();
    }
}
