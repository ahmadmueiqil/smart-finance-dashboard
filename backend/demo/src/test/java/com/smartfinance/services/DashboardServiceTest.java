package com.smartfinance.services;

import com.smartfinance.dto.DashboardDto;
import com.smartfinance.entities.Bill;
import com.smartfinance.entities.Transactions;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.UserNotFoundException;
import com.smartfinance.repositories.BillRepository;
import com.smartfinance.repositories.TransactionRepository;
import com.smartfinance.repositories.UserRepository;
import com.smartfinance.repositories.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BillRepository billRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletService walletService;

    @Mock
    private BillService billService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void shouldReturnDashboardSuccessfully() {

        Wallet wallet = new Wallet();
        wallet.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setWallet(wallet);

        Transactions transaction = new Transactions();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(100));

        when(authentication.getName())
                .thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        when(walletService.getBalance(authentication))
                .thenReturn(BigDecimal.valueOf(1000));

        when(transactionRepository.sumByUserIdAndTransactionType(
                wallet.getId(),
                Transactions.TransactionType.EXPENSE
        )).thenReturn(BigDecimal.valueOf(200));

        when(transactionRepository.sumByUserIdAndTransactionType(
                wallet.getId(),
                Transactions.TransactionType.TRANSFER_IN
        )).thenReturn(BigDecimal.valueOf(500));

        when(transactionRepository.sumByUserIdAndTransactionType(
                wallet.getId(),
                Transactions.TransactionType.TRANSFER_OUT
        )).thenReturn(BigDecimal.valueOf(100));

        when(billRepository.countByUserIdAndStatus(
                user.getId(),
                Bill.Status.PENDING
        )).thenReturn(2);

        when(transactionRepository.findUserTransactions(
                eq(wallet.getId()),
                any()
        )).thenReturn(
                new PageImpl<>(
                        List.of(transaction)
                )
        );

        when(
                billService.getAllBills(
                        any(),
                        any(),
                        anyInt(),
                        anyInt()
                )
        ).thenReturn(Page.empty());

        DashboardDto dashboard =
                dashboardService.getDashboard(authentication);

        assertNotNull(dashboard);

        assertEquals(
                BigDecimal.valueOf(1000),
                dashboard.getBalance()
        );

        assertEquals(
                BigDecimal.valueOf(200),
                dashboard.getTotalExpanses()
        );

        assertEquals(
                BigDecimal.valueOf(500),
                dashboard.getTotalTransferIn()
        );

        assertEquals(
                BigDecimal.valueOf(100),
                dashboard.getTotalTransferOut()
        );

        assertEquals(
                2,
                dashboard.getPindingBills()
        );

        assertEquals(
                1,
                dashboard.getLastTransactions()
                        .getContent()
                        .size()
        );
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        when(authentication.getName())
                .thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> dashboardService.getDashboard(authentication)
        );
    }

    @Test
    void shouldReturnZeroWhenNoExpensesExist() {

        Wallet wallet = new Wallet();
        wallet.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setWallet(wallet);

        when(authentication.getName())
                .thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        when(walletService.getBalance(authentication))
                .thenReturn(BigDecimal.ZERO);

        when(transactionRepository.sumByUserIdAndTransactionType(
                wallet.getId(),
                Transactions.TransactionType.EXPENSE
        )).thenReturn(null);

        when(transactionRepository.sumByUserIdAndTransactionType(
                wallet.getId(),
                Transactions.TransactionType.TRANSFER_IN
        )).thenReturn(BigDecimal.ZERO);

        when(transactionRepository.sumByUserIdAndTransactionType(
                wallet.getId(),
                Transactions.TransactionType.TRANSFER_OUT
        )).thenReturn(BigDecimal.ZERO);

        when(billRepository.countByUserIdAndStatus(
                anyLong(),
                any()
        )).thenReturn(0);

        when(transactionRepository.findUserTransactions(
                anyLong(),
                any()
        )).thenReturn(
                new PageImpl<>(
                        List.of()
                )
        );

        when(
                billService.getAllBills(
                        any(),
                        any(),
                        anyInt(),
                        anyInt()
                )
        ).thenReturn(Page.empty());

        DashboardDto dashboard =
                dashboardService.getDashboard(authentication);

        assertEquals(
                BigDecimal.ZERO,
                dashboard.getTotalExpanses()
        );
    }
}