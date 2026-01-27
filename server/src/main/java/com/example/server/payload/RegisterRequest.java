package com.example.server.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    @NotBlank(message = "user name required.")
    @Size(min = 4, message = "user name must contain at least 4 caracters.")
    private String username;

    @Email(message = "your email is not valid.")
    @NotBlank(message = "Email required.")
    private String email;

    @NotBlank(message = "Password required")
    private String password;
}
