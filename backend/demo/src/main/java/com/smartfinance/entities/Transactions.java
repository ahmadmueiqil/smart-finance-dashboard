package com.smartfinance.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transactions")
public class Transactions {

    public enum TransactionType{
        DEPOSIT,
        EXPENSE ,
        TRANSFER_OUT,
        TRANSFER_IN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "sender_wallet_id", nullable = true)
    private Wallet sender;

    @ManyToOne
    @JoinColumn(name = "receiver_wallet_id" , nullable = true)
    private Wallet receiver;

    private String description;

    private LocalDateTime createdAt;


    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
