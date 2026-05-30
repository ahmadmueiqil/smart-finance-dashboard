package com.smartfinance.services;

import com.smartfinance.dto.TransferMoneyRequest;
import com.smartfinance.entities.Transactions;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.InsufficientBalanceException;
import com.smartfinance.exception.WalletNotFoundException;
import com.smartfinance.repositories.BillRepository;
import com.smartfinance.repositories.TransactionRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BillRepository billRepository;

    @Mock
    private WalletService walletService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TransactionService transactionService;


    @Test
    void shouldTransferMoneySuccessfully() {

        User user = new User();

        Wallet fromWallet = new Wallet();
        fromWallet.setId(1L);
        fromWallet.setBalance(BigDecimal.valueOf(1000));

        Wallet toWallet = new Wallet();
        toWallet.setId(2L);

        user.setWallet(fromWallet);

        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setToWalletId(2L);
        request.setAmount(BigDecimal.valueOf(100));

        when(authentication.getName()).thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        when(walletRepository.findById(2L))
                .thenReturn(Optional.of(toWallet));

        transactionService.transferMoney(request, authentication);

        verify(walletService).withdraw(BigDecimal.valueOf(100), fromWallet);
        verify(walletService).deposit(BigDecimal.valueOf(100), toWallet);

        verify(transactionRepository, times(2)).save(any(Transactions.class));
    }


    @Test
    void shouldThrowExceptionWhenWalletNotFound() {

        User user = new User();

        Wallet fromWallet = new Wallet();
        fromWallet.setBalance(BigDecimal.valueOf(1000));

        user.setWallet(fromWallet);

        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setToWalletId(99L);
        request.setAmount(BigDecimal.valueOf(100));

        when(authentication.getName()).thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        when(walletRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                WalletNotFoundException.class,
                () -> transactionService.transferMoney(request, authentication)
        );
    }


    @Test
    void shouldThrowExceptionWhenBalanceIsInsufficient() {

        User user = new User();

        Wallet fromWallet = new Wallet();
        fromWallet.setBalance(BigDecimal.valueOf(50));

        Wallet toWallet = new Wallet();

        user.setWallet(fromWallet);

        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setToWalletId(2L);
        request.setAmount(BigDecimal.valueOf(100));

        when(authentication.getName()).thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        when(walletRepository.findById(2L))
                .thenReturn(Optional.of(toWallet));

        assertThrows(
                InsufficientBalanceException.class,
                () -> transactionService.transferMoney(request, authentication)
        );
    }


    @Test
    void shouldThrowExceptionWhenAmountIsZero() {

        User user = new User();

        Wallet fromWallet = new Wallet();
        fromWallet.setBalance(BigDecimal.valueOf(1000));

        Wallet toWallet = new Wallet();

        user.setWallet(fromWallet);

        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setToWalletId(2L);
        request.setAmount(BigDecimal.ZERO);

        when(authentication.getName()).thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        when(walletRepository.findById(2L))
                .thenReturn(Optional.of(toWallet));

        assertThrows(
                RuntimeException.class,
                () -> transactionService.transferMoney(request, authentication)
        );
    }
}