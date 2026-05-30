package com.smartfinance.services;


import com.smartfinance.dto.BillPaymentRequest;
import com.smartfinance.dto.CreateBillRequest;
import com.smartfinance.entities.Bill;
import com.smartfinance.entities.Transactions;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.*;
import com.smartfinance.repositories.BillRepository;
import com.smartfinance.repositories.TransactionRepository;
import com.smartfinance.repositories.UserRepository;
import com.smartfinance.repositories.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BillServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BillRepository billRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private WalletRepository walletRepository;


    @InjectMocks
    private BillService billService;

    @Test
    void billPayment_shouldPayBillSuccessfully(){

        //Arrange
        Authentication authentication = mock(Authentication.class);

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("500"));

        User user = new User();
        user.setWallet(wallet);
        user.setId(20l);
        user.setEmail("test@test.com");

        Bill bill = new Bill();
        bill.setId(1l);
        bill.setStatus(Bill.Status.PENDING);
        bill.setUser(user);
        bill.setAmount(new BigDecimal("100"));

        when(authentication.getName())
                .thenReturn("test@test.com");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        when(billRepository.findById(bill.getId()))
                .thenReturn(Optional.of(bill));

        BillPaymentRequest paymentRequest = new BillPaymentRequest();
        paymentRequest.setBillId(bill.getId());

        //Act
        billService.billPayment(paymentRequest, authentication);

        //Assert

        assertTrue(wallet.getBalance().compareTo(new BigDecimal("400")) == 0);
        assertTrue(bill.getStatus().equals(Bill.Status.PAID));

        verify(transactionRepository).save(any(Transactions.class));
        verify(billRepository).save(bill);
        verify(walletRepository).save(wallet);
    }

    @Test
    void billPayment_shouldThrowExceptionWhenBalanceIsInsufficient(){
        //Arrange
        Authentication authentication = mock(Authentication.class);

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("100"));

        User user = new User();
        user.setId(20l);
        user.setWallet(wallet);
        user.setEmail("test@test.com");

        Bill bill = new Bill();
        bill.setAmount(new BigDecimal("500"));
        bill.setStatus(Bill.Status.PENDING);
        bill.setUser(user);
        bill.setId(1l);

        when(authentication.getName())
                .thenReturn("test@test.com");
        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(billRepository.findById(bill.getId()))
                .thenReturn(Optional.of(bill));

        BillPaymentRequest paymentRequest = new BillPaymentRequest();
        paymentRequest.setBillId(bill.getId());

        //Act + assert
        assertThrows(InsufficientBalanceException.class,
                () -> billService.billPayment(paymentRequest, authentication));

        verify(transactionRepository, never()).save(any(Transactions.class));
        verify(billRepository, never()).save(bill);
        verify(walletRepository,never()).save(wallet);
    }

    @Test
    void billPayment_shouldThrowExceptionWhenBillIsAlreadyPaid(){
        //Arrange
        Authentication authentication = mock(Authentication.class);

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("500"));

        User user = new User();
        user.setId(20l);
        user.setWallet(wallet);
        user.setEmail("test@test.com");

        Bill bill = new Bill();
        bill.setAmount(new BigDecimal("100"));
        bill.setStatus(Bill.Status.PAID);
        bill.setUser(user);
        bill.setId(1l);

        when(authentication.getName())
                .thenReturn("test@test.com");
        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(billRepository.findById(bill.getId()))
                .thenReturn(Optional.of(bill));

        BillPaymentRequest paymentRequest = new BillPaymentRequest();
        paymentRequest.setBillId(bill.getId());

        //Act + assert
        assertThrows(BillAlreadyPaidException.class,
                () -> billService.billPayment(paymentRequest, authentication));

        verify(transactionRepository, never()).save(any(Transactions.class));
        verify(billRepository, never()).save(bill);
        verify(walletRepository,never()).save(wallet);
    }

    @Test
    void billPayment_shouldThrowExceptionWhenBillIsNotFound(){
        //Arrange
        Authentication authentication = mock(Authentication.class);

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("500"));

        User user = new User();
        user.setId(20l);
        user.setWallet(wallet);
        user.setEmail("test@test.com");


        when(authentication.getName())
                .thenReturn("test@test.com");
        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));
        when(billRepository.findById(1l))
                .thenReturn(Optional.empty());

        BillPaymentRequest paymentRequest = new BillPaymentRequest();
        paymentRequest.setBillId(1l);

        //Act + assert
        assertThrows(BillNotFoundException.class,
                () -> billService.billPayment(paymentRequest, authentication));

        verify(transactionRepository, never()).save(any(Transactions.class));
        verify(billRepository, never()).save(any(Bill.class));
        verify(walletRepository,never()).save(any(Wallet.class));
    }

    @Test
    void billPayment_shouldThrowExceptionWhenUserIsntAutharizedToPay(){
        //Arrange
        Authentication authentication = mock(Authentication.class);

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("500"));

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(new BigDecimal("500"));

        User user = new User();
        user.setId(20l);
        user.setWallet(wallet);
        user.setEmail("test@test.com");

        User user1 = new User();
        user1.setId(10l);
        user1.setWallet(wallet1);
        user1.setEmail("test1@test1.com");

        Bill bill = new Bill();
        bill.setAmount(new BigDecimal("100"));
        bill.setStatus(Bill.Status.PENDING);
        bill.setUser(user);
        bill.setId(1l);

        Bill bill1 = new Bill();
        bill1.setAmount(new BigDecimal("100"));
        bill1.setStatus(Bill.Status.PENDING);
        bill1.setUser(user1);
        bill1.setId(1l);

        when(authentication.getName())
                .thenReturn("test@test.com");
        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user1));
        when(billRepository.findById(bill.getId()))
                .thenReturn(Optional.of(bill));

        BillPaymentRequest paymentRequest = new BillPaymentRequest();
        paymentRequest.setBillId(bill.getId());

        //Act + assert
        assertThrows(UnauthorizedBillAccessException.class,
                () -> billService.billPayment(paymentRequest, authentication));

        verify(transactionRepository, never()).save(any(Transactions.class));
        verify(billRepository, never()).save(bill);
        verify(walletRepository,never()).save(wallet);
    }


    //Tests For Create Bill

    @Test
    void createBill_shouldCreateBillSuccessfully(){

        //Arrange
        Authentication authentication = mock(Authentication.class);

        User user = new User();
        user.setEmail("test@test.com");

        when(authentication.getName())
                .thenReturn("test@test.com");
        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        CreateBillRequest createBillRequest = new CreateBillRequest();
        createBillRequest.setAmount(new BigDecimal("100"));
        createBillRequest.setCategory(Bill.BillType.ELECTRICITY);

        ArgumentCaptor<Bill> argumentCaptor =
                ArgumentCaptor.forClass(Bill.class);


        //Act
        billService.createBill(createBillRequest,authentication);

        //Assert
        verify(billRepository).save(argumentCaptor.capture());

        Bill savedBill = argumentCaptor.getValue();

        assertTrue(savedBill.getAmount().compareTo(new BigDecimal("100")) == 0);
        assertEquals(Bill.Status.PENDING,
                savedBill.getStatus());
        assertEquals(Bill.BillType.ELECTRICITY,
                savedBill.getBillType());
        assertEquals(user,
                savedBill.getUser());
    }

    @Test
    void createBill_shouldThrowExceptionWhenAmountZeroOrLess(){
        //Arrange
        Authentication authentication = mock(Authentication.class);

        User user = new User();
        user.setEmail("test@test.com");

        when(authentication.getName())
                .thenReturn("test@test.com");
        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        CreateBillRequest createBillRequest = new CreateBillRequest();
        createBillRequest.setAmount(BigDecimal.ZERO);
        createBillRequest.setCategory(Bill.BillType.ELECTRICITY);

        //Act + Assert

        assertThrows(InvalidBillAmountException.class,
                () -> billService.createBill(createBillRequest, authentication));

        verify(billRepository, never()).save(any(Bill.class));
    }

    @Test
    void createBill_shouldThrowExceptionWhenUserNotFound(){
        //Arrange
        Authentication authentication = mock(Authentication.class);

        User user = new User();
        user.setEmail("test@test.com");

        when(authentication.getName())
                .thenReturn("test@test.com");
        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.empty());

        CreateBillRequest createBillRequest = new CreateBillRequest();
        createBillRequest.setAmount(new BigDecimal("100"));
        createBillRequest.setCategory(Bill.BillType.ELECTRICITY);

        //Act + Assert

        assertThrows(UserNotFoundException.class,
                () -> billService.createBill(createBillRequest, authentication));

        verify(billRepository, never()).save(any(Bill.class));
    }

}
