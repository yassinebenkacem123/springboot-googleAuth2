package com.example.server.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.server.payload.APIResponse;
import com.example.server.payload.LoginRequest;
import com.example.server.payload.LoginResponse;
import com.example.server.payload.RegisterRequest;
import com.example.server.repositories.UserRepo;
import com.example.server.security.jwt.JwtUtils;
import com.example.server.security.services.UserDetailsImpl;


import com.example.server.exceptions.APIException;
import com.example.server.models.User;
@Service
public class AuthServiceImplt  implements AuthService{

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<?> registerNewUser(RegisterRequest registerRequest) {
        if(userRepo.existsByUsername(registerRequest.getUsername())){
            throw new APIException("User with the name "+registerRequest.getUsername()+" Already exist.");
        }else if(userRepo.existsByEmail(registerRequest.getEmail())){
            throw new APIException("User with the email "+registerRequest.getEmail()+" Already exist.");

        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        
        // must be hashed.
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User savedUser = userRepo.save(user);
        return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> loginService(LoginRequest loginRequest) {
        Authentication authentication;
        try{
            User user = userRepo.findByEmail(loginRequest.getEmail());
            if(user == null){
                throw new APIException("User not found with email "+loginRequest.getEmail());
            }
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    loginRequest.getPassword()
                )
            );
            
        }catch(AuthenticationException e){
            APIResponse apiResponse = new APIResponse();
            apiResponse.setMessage("Password incorrect.");
            apiResponse.setStatus(false);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie cookie =  jwtUtils.stockingJwtOnCookie(userDetails);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setEmail(userDetails.getEmail());
        loginResponse.setUsername(userDetails.getUsername());
        loginResponse.setId(userDetails.getId());
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage("User logined successfly.");
        apiResponse.setStatus(true);
        loginResponse.setApiResponse(apiResponse);
        return ResponseEntity
            .ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(loginResponse);
    }
    
}
