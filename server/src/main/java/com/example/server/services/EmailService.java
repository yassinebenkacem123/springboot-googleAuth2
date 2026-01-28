package com.example.server.services;

public interface EmailService {

    void send(String to, String subject, String body);

    
} 
