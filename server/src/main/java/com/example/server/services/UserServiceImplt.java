package com.example.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.server.exceptions.ResourceNotFoundException;
import com.example.server.models.User;
import com.example.server.repositories.UserRepo;

@Service
public class UserServiceImplt implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public ResponseEntity<?> getUsers() {
        List<User> users  = userRepo.findAll();
        if(users.isEmpty()){

            throw new ResourceNotFoundException("No user found");
        }


        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
