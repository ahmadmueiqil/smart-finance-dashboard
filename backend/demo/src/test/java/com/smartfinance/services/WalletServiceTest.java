package com.smartfinance.services;


import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.InsufficientBalanceException;
import com.smartfinance.exception.UserNotFoundException;
import com.smartfinance.repositories.UserRepository;
import com.smartfinance.repositories.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {


    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WalletService walletService;


    @Test
    void withdraw_shouldSubtractAmountSucessfully(){

        //Arrange
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("100"));

        BigDecimal amount = new BigDecimal("50");

        //Act
        walletService.withdraw(amount, wallet);

        assertTrue(wallet.getBalance().compareTo(new BigDecimal("50")) == 0);
    }

    @Test
    void withdraw_shouldThrowExceptionWhenBalanceIsInsufficient(){
        //Arrange
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("50"));

        BigDecimal amount = new BigDecimal("100");

        //Act + Assert
        assertThrows(InsufficientBalanceException.class,
                () -> walletService.withdraw(amount, wallet));


    }

    @Test
    void deposit_shouldAddAmountSuccessfully(){

        //arrange
        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("100"));

        BigDecimal amount = new BigDecimal("50");

        //act
        walletService.deposit(amount, wallet);

        //assert
        assertTrue(wallet.getBalance().compareTo(new BigDecimal("150")) == 0);

    }

    @Test
    void getBalance_shouldReturnWalletBalance(){


        //arrange
        Authentication authentication = mock(Authentication.class);

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("500"));

        User user = new User();
        user.setEmail("test@test.com");
        user.setWallet(wallet);

        when(authentication.getName())
                .thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        //Act
            BigDecimal balance = walletService.getBalance(authentication);

        //Assert
        assertTrue(balance.compareTo(wallet.getBalance()) == 0);
    }

    @Test
    void getBalance_shouldReturnUserNotFoundException(){

        //Arrange
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName())
                .thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.empty());

        //Assert + Act
        assertThrows(UserNotFoundException.class,
                () -> walletService.getBalance(authentication));

    }
}
