package com.smartfinance.services;


import com.smartfinance.dto.DashboardDto;
import com.smartfinance.dto.TransactionDto;
import com.smartfinance.entities.Bill;
import com.smartfinance.entities.Transactions;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.UserNotFoundException;
import com.smartfinance.exception.WalletNotFoundException;
import com.smartfinance.repositories.BillRepository;
import com.smartfinance.repositories.TransactionRepository;
import com.smartfinance.repositories.UserRepository;
import com.smartfinance.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final WalletService walletService;
    private final BillService billService;



    private User getUser(Authentication authentication){
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private BigDecimal getTotalExpanses(User user){


        BigDecimal totalExpanses = transactionRepository.sumByUserIdAndTransactionType(user.getWallet().getId(), Transactions.TransactionType.EXPENSE);


        return totalExpanses != null ? totalExpanses : BigDecimal.ZERO;
    }

    private BigDecimal getTotalDeposits(User user){


        BigDecimal totalDeposits = transactionRepository.sumByUserIdAndTransactionType(user.getWallet().getId(), Transactions.TransactionType.DEPOSIT);


        return totalDeposits != null ? totalDeposits : BigDecimal.ZERO;
    }


    private BigDecimal getTotalTransferIn(User user){


        BigDecimal totalTransferIn = transactionRepository.sumByUserIdAndTransactionType(user.getWallet().getId(), Transactions.TransactionType.TRANSFER_IN);


        return totalTransferIn != null ? totalTransferIn : BigDecimal.ZERO;
    }

    private BigDecimal getTotalTransferout(User user){


        BigDecimal totalTransferOut = transactionRepository.sumByUserIdAndTransactionType(user.getWallet().getId(), Transactions.TransactionType.TRANSFER_OUT);


        return totalTransferOut != null ? totalTransferOut : BigDecimal.ZERO;
    }

    private int getCountOfPindingBills(User user){

        Long userId = user.getId();

        int countOfPindingBills = billRepository.countByUserIdAndStatus(userId, Bill.Status.PENDING);

        return countOfPindingBills;
    }

    private Page<TransactionDto> getLast5Transactions(User user){

        Wallet wallet = user.getWallet();
        Page<Transactions> transactions = transactionRepository.findUserTransactions(wallet.getId(), PageRequest.of(0, 5));

        return transactions.map(transaction -> {
            TransactionDto dto = new TransactionDto();
            dto.setId(transaction.getId());
            dto.setTransactionType(transaction.getTransactionType());
            dto.setAmount(transaction.getAmount());
            dto.setDescription(transaction.getDescription());
            dto.setCreatedAt(transaction.getCreatedAt());

            if (transaction.getSender() != null)
            dto.setFromWalletId(transaction.getSender().getId());

            if (transaction.getReceiver() != null)
            dto.setToWalletId(transaction.getReceiver().getId());

            return dto;
        });

    }

    public DashboardDto getDashboard(Authentication authentication){

        User user = getUser(authentication);

        DashboardDto dashboardDto = new DashboardDto();

        dashboardDto.setBalance(walletService.getBalance(authentication));
        dashboardDto.setPindingBills(getCountOfPindingBills(user));
        dashboardDto.setLastTransactions(getLast5Transactions(user));
        dashboardDto.setTotalTransferIn(getTotalTransferIn(user));
        dashboardDto.setTotalTransferOut(getTotalTransferout(user));
        dashboardDto.setTotalExpanses(getTotalExpanses(user));
        dashboardDto.setLastBills(billService.getAllBills(authentication,null,0,5));

        return dashboardDto;
    }

}
