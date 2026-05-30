package com.smartfinance.services;


import com.smartfinance.JWT.JwtService;
import com.smartfinance.dto.RegisterRequest;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.repositories.UserRepository;
import com.smartfinance.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {


    @Mock
    private  UserRepository userRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private  JwtService jwtService;

    @Mock
    private  AuthenticationManager authenticationManager;

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private AuthService authService;


    @Test
    void register_shouldRegisterUserSuccessfully(){

        //Arrange
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@test.com");
        request.setUsername("test");
        request.setPassword("testtest");

        when(userRepository.existsByEmail("test@test.com"))
                .thenReturn(false);
        when(userRepository.existsByUsername("test"))
                .thenReturn(false);
        when(passwordEncoder.encode("testtest"))
                .thenReturn("encoded_password");
        when(jwtService.generateToken(any(User.class)))
                .thenReturn("fake_token");

        ArgumentCaptor<User> userCaptor =
                ArgumentCaptor.forClass(User.class);

        //Act
        String token =  authService.register(request);

        //Assert
        verify(userRepository).save(userCaptor.capture());

        User userSaved = userCaptor.getValue();



        assertEquals("test@test.com",
                userSaved.getEmail() );
        assertEquals("encoded_password",
                userSaved.getPassword());
        assertEquals("test",
                userSaved.getRealUsername());
        assertEquals(User.Rule.USER,
                userSaved.getRole());
        assertNotNull(userSaved.getWallet());
        assertTrue(userSaved.getWallet().getBalance()
                .compareTo(BigDecimal.ZERO) == 0);
        assertEquals("fake_token", token);

    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@test.com");

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> authService.register(request)
        );

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");

        when(userRepository.existsByEmail(anyString()))
                .thenReturn(false);

        when(userRepository.existsByUsername(request.getUsername()))
                .thenReturn(true);

        assertThrows(
                RuntimeException.class,
                () -> authService.register(request)
        );

    }

    @Test
    void shouldEncodePasswordBeforeSaving() {

        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@test.com");
        request.setUsername("testuser");
        request.setPassword("123456");

        when(userRepository.existsByEmail(anyString()))
                .thenReturn(false);

        when(userRepository.existsByUsername(anyString()))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("encodedPassword");

        authService.register(request);

        verify(passwordEncoder).encode("123456");
    }

}
