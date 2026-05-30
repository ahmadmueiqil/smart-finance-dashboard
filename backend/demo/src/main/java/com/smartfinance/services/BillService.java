package com.smartfinance.services;


import com.smartfinance.dto.BillDto;
import com.smartfinance.dto.CreateBillRequest;
import com.smartfinance.dto.BillPaymentRequest;
import com.smartfinance.entities.Bill;
import com.smartfinance.entities.Transactions;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.*;
import com.smartfinance.repositories.BillRepository;
import com.smartfinance.repositories.TransactionRepository;
import com.smartfinance.repositories.UserRepository;
import com.smartfinance.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.smartfinance.entities.Bill.Status.PAID;

@Service
@RequiredArgsConstructor
public class BillService {

    private final UserRepository userRepository;
    private final BillRepository billRepository;
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    private User getUser(Authentication authentication){
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public void createBill(CreateBillRequest request,
                           Authentication authentication) {

        User user = getUser(authentication);

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidBillAmountException("Ammount must be grater than zero");
        }

        Bill bill = new Bill();

        bill.setAmount(request.getAmount());
        bill.setBillType(request.getCategory());
        bill.setUser(user);
        bill.setStatus(Bill.Status.PENDING);
        bill.setTitle(request.getBillTitle());

        billRepository.save(bill);

    }

    public Page<BillDto> getAllBills(Authentication authentication,
                                     Bill.Status status,
                                     int pageNumber,
                                     int pageSize) {
        Page<Bill> bills;

        User user = getUser(authentication);

        if (status != null) {
            bills = billRepository.findByUserIdAndStatus(user.getId(), status , PageRequest.of(pageNumber,pageSize, Sort.by("createdAt").descending()));
        } else {
            bills = billRepository.findByUserId(user.getId(), PageRequest.of(pageNumber,pageSize, Sort.by("createdAt").descending()));
        }

        return bills.map(bill -> {
            BillDto billDto = new BillDto();
            billDto.setId(bill.getId());
            billDto.setAmount(bill.getAmount());
            billDto.setStatus(bill.getStatus());
            billDto.setBillType(bill.getBillType());
            billDto.setCreatedAt(bill.getCreatedAt());
            billDto.setTitle(bill.getTitle());
            return billDto;
        });
    }

    @Transactional
    public void billPayment(BillPaymentRequest request,
                            Authentication authentication){


        User user = getUser(authentication);

        Wallet wallet = user.getWallet();

        Bill bill = billRepository.findById(request.getBillId())
                .orElseThrow(() -> new BillNotFoundException("Bill not found with id " + request.getBillId()));

        if (wallet.getBalance().compareTo(bill.getAmount()) < 0){
            throw new InsufficientBalanceException("Insufficient balance ");
        }

        if (bill.getStatus() == PAID){
            throw new BillAlreadyPaidException("Bill Already Paid");
        }

        if (!bill.getUser().getId().equals(user.getId()) ){
            throw new UnauthorizedBillAccessException("Unauthorized Bill Access");
        }


            wallet.setBalance(wallet.getBalance().subtract(bill.getAmount()));
            bill.setStatus(PAID);

            Transactions transaction = new Transactions();
            transaction.setTransactionType(Transactions.TransactionType.EXPENSE);
            transaction.setAmount(bill.getAmount());
            transaction.setSender(wallet);
            transaction.setReceiver(null);
            transaction.setDescription("Pay "+ bill.getBillType() +" bill with amount "+ bill.getAmount() +" JOD");
            transaction.setCreatedAt(LocalDateTime.now());
            transactionRepository.save(transaction);

            walletRepository.save(wallet);
            billRepository.save(bill);


    }





}
