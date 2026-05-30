package com.smartfinance.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is recuired")
    @Email(message = "Invalid Email Format")
    private String email;


    @NotBlank(message = "Password Is Recuired")
    @Size(min = 6, message = "Password must be more then 6 character")
    private String password;
}
