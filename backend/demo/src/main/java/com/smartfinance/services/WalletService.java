package com.smartfinance.services;


import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.InsufficientBalanceException;
import com.smartfinance.exception.UserNotFoundException;
import com.smartfinance.repositories.UserRepository;
import com.smartfinance.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    private Wallet getWallet(Authentication authentication){
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found")).getWallet();
    }

    public void withdraw(BigDecimal amount, Wallet wallet){
        if (wallet.getBalance().compareTo(amount) < 0){
            throw new InsufficientBalanceException("Insufficient Balance");
        }
        wallet.setBalance(wallet.getBalance().subtract(amount));
    }

    public void deposit(BigDecimal amount, Wallet wallet){
        wallet.setBalance(wallet.getBalance().add(amount));
    }

    public BigDecimal getBalance(Authentication authentication){
        Wallet wallet = getWallet(authentication);

        return wallet.getBalance();
    }

}
