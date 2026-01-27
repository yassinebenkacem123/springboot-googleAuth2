package com.example.server.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Email(message = "your email is not valid.")
    @NotBlank(message = "Email required.")
    private String email;

    @NotBlank(message = "Password required")
    private String password;
}
