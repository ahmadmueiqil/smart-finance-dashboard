package com.smartfinance.Controllers;


import com.smartfinance.dto.ApiResponse;
import com.smartfinance.dto.AuthResponse;
import com.smartfinance.dto.LoginRequest;
import com.smartfinance.dto.RegisterRequest;
import com.smartfinance.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;



    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request){


        String token = authService.login(request);

        return ResponseEntity.ok(
                new ApiResponse<>("Login Successfuly",
                        token,
                        true
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request){

        String token = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>("Registered Successfuly",
                        token,
                        true
                )
        );
    }
}
