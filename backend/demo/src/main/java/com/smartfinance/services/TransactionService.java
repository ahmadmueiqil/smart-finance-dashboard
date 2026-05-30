package com.smartfinance.services;

import com.smartfinance.dto.*;
import com.smartfinance.entities.Bill;
import com.smartfinance.entities.Transactions;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.InsufficientBalanceException;
import com.smartfinance.exception.UserNotFoundException;
import com.smartfinance.exception.WalletNotFoundException;
import com.smartfinance.repositories.BillRepository;
import com.smartfinance.repositories.TransactionRepository;
import com.smartfinance.repositories.UserRepository;
import com.smartfinance.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.smartfinance.entities.Bill.Status.PAID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BillRepository billRepository;
    private final WalletService walletService;


    private Wallet getWallet(Authentication authentication){
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found")).getWallet();
    }

    public Page<TransactionDto> getAllTransactions(Authentication authentication,
                                                   int pageNumber,
                                                   int pageSize,
                                                   Transactions.TransactionType type) {

        Page<Transactions> transactions;


        if (type != null) {

            transactions =
                    transactionRepository
                            .findUserTransactionsByType(
                                    getWallet(authentication).getId(),
                                    type,
                                    PageRequest.of(pageNumber, pageSize)
                            );

        } else {

            transactions =
                    transactionRepository
                            .findUserTransactions(
                                    getWallet(authentication).getId(),
                                    PageRequest.of(pageNumber, pageSize)
                            );

        }


        return transactions.map(transaction -> {
            TransactionDto dto = new TransactionDto();
            dto.setAmount(transaction.getAmount());
            dto.setTransactionType(transaction.getTransactionType());
            dto.setId(transaction.getId());
            dto.setDescription(transaction.getDescription());
            dto.setCreatedAt(transaction.getCreatedAt());

            if (transaction.getSender() != null)
                dto.setFromWalletId(transaction.getSender().getId());

            if (transaction.getReceiver() != null)
                dto.setToWalletId(transaction.getReceiver().getId());

            return dto;
        });
    }




    public Transactions createTransactionRecord(Transactions.TransactionType type,
                                                BigDecimal amount,
                                                Wallet receiver,
                                                Wallet sender,
                                                String description){

        Transactions transaction = new Transactions();
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setReceiver(receiver);
        transaction.setSender(sender);
        transaction.setDescription("Transfer money from wallet id " + sender.getId() +
                " to wallet id " + receiver.getId());

        return transaction;
    }

    @Transactional
    public void transferMoney(TransferMoneyRequest request,
                              Authentication authentication) {


        Wallet fromWallet = getWallet(authentication);

        Long toWalletId = request.getToWalletId();

        Wallet toWallet = walletRepository.findById(toWalletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Ammount sholed be uppove the zero");
        }

        if (fromWallet.getBalance().compareTo(request.getAmount()) <= 0) {
            throw new InsufficientBalanceException("Insafeciant balance");
        }

        if(fromWallet.getId() == request.getToWalletId()){
            throw new RuntimeException("Invalid Wallet Id");
        }

        walletService.withdraw(request.getAmount(), fromWallet);
        walletService.deposit(request.getAmount(), toWallet);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);


        Transactions transaction =  createTransactionRecord(Transactions.TransactionType.TRANSFER_OUT,
                request.getAmount(),
                toWallet,
                fromWallet,
                "Transfer money from wallet id " + fromWallet.getId() +
                        " to wallet id " + toWallet.getId());

        Transactions transaction2 =  createTransactionRecord(Transactions.TransactionType.TRANSFER_IN,
                request.getAmount(),
                toWallet,
                fromWallet,
                "Transfer money from wallet id " + fromWallet.getId() +
                        " to wallet id " + toWallet.getId());

        transactionRepository.save(transaction);
        transactionRepository.save(transaction2);

    }


}

