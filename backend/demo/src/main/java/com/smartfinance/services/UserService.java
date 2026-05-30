package com.smartfinance.services;

import com.smartfinance.dto.ProfileResponse;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.UserNotFoundException;
import com.smartfinance.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.smartfinance.dto.UpdateProfileRequest;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ProfileResponse getProfile(
            Authentication authentication
    ) {

        User user = userRepository
                .findByEmail(
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );

        Wallet wallet = user.getWallet();

        ProfileResponse response =
                new ProfileResponse();

        response.setUsername(
                user.getRealUsername()
        );

        response.setEmail(
                user.getEmail()
        );

        response.setBalance(
                wallet.getBalance()
        );

        response.setWalletId(
                wallet.getId()
        );

        return response;

    }


    public void updateProfile(
            UpdateProfileRequest request,
            Authentication authentication
    ) {

        User user = userRepository
                .findByEmail(
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"
                        )
                );

        // username check
        if (
                !user.getRealUsername().equals(request.getUsername())
                        &&
                        userRepository.existsByUsername(
                                request.getUsername()
                        )
        ) {

            throw new RuntimeException(
                    "Username already exists"
            );

        }

        // email check
        if (
                !user.getEmail().equals(request.getEmail())
                        &&
                        userRepository.existsByEmail(
                                request.getEmail()
                        )
        ) {

            throw new RuntimeException(
                    "Email already exists"
            );

        }

        user.setUsername(
                request.getUsername()
        );

        user.setEmail(
                request.getEmail()
        );

        userRepository.save(user);

    }
}