package com.smartfinance.dto;


import com.smartfinance.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatches
public class RegisterRequest {

    @NotBlank(message = "user name is requiered")
    private String username;

    @Email(message = "Invalid Email Formate")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, message = "password should be more than sex character or Equal")
    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "confirm password is required")
    private String confirmPassword;
}
