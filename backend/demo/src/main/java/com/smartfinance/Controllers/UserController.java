package com.smartfinance.Controllers;

import com.smartfinance.dto.ApiResponse;
import com.smartfinance.dto.ProfileResponse;
import com.smartfinance.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.smartfinance.dto.UpdateProfileRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(
            Authentication authentication
    ) {

        ProfileResponse response =
                userService.getProfile(
                        authentication
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Profile fetched successfully",
                        response,
                        true
                )
        );

    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<Object>> updateProfile(
            @Valid
            @RequestBody
            UpdateProfileRequest request,

            Authentication authentication
    ) {

        userService.updateProfile(
                request,
                authentication
        );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Profile updated successfully",
                        List.of(),
                        true
                )
        );

    }

}