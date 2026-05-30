package com.smartfinance.dto;

import com.smartfinance.entities.Transactions;
import com.smartfinance.entities.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {

    private Long id;
    private BigDecimal amount;
    private Transactions.TransactionType transactionType;
    @Null
    private Long fromWalletId;
    @Null
    private Long toWalletId;
    private String description;
    private LocalDateTime createdAt;

}


