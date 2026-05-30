package com.smartfinance.services;


import com.smartfinance.JWT.JwtService;
import com.smartfinance.dto.LoginRequest;
import com.smartfinance.dto.RegisterRequest;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.InvalidEamilOrPasswordException;
import com.smartfinance.repositories.UserRepository;
import com.smartfinance.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    @Transactional
    public String register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException("username already exists");
        }

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setRole(User.Rule.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setWallet(wallet);
        wallet.setUser(user);

        userRepository.save(user);


        return jwtService.generateToken(user);
    }

    public String login(LoginRequest request){

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();

            return jwtService.generateToken(user);

        } catch (Exception e){
            throw new InvalidEamilOrPasswordException("Invalid email or password");
        }
    }
}
