package com.smartfinance.repositories;

import com.smartfinance.entities.Transactions;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    Optional<com.smartfinance.entities.Transactions> findBySender(Wallet wallet);

    Optional<com.smartfinance.entities.Transactions> findByReceiver(Wallet wallet);






    @Query("""
SELECT COALESCE(SUM(t.amount), 0)
FROM Transactions t
WHERE

t.transactionType = :type

AND

(

    (
        t.transactionType IN (
            com.smartfinance.entities.Transactions.TransactionType.TRANSFER_OUT,
            com.smartfinance.entities.Transactions.TransactionType.EXPENSE
        )

        AND

        t.sender.id = :walletId
    )

    OR

    (
        t.transactionType IN (
            com.smartfinance.entities.Transactions.TransactionType.TRANSFER_IN,
            com.smartfinance.entities.Transactions.TransactionType.DEPOSIT
        )

        AND

        t.receiver.id = :walletId
    )

)
""")
    BigDecimal sumByUserIdAndTransactionType(
            @Param("walletId") Long walletId,

            @Param("type")
            Transactions.TransactionType type
    );


    @Query("""
SELECT t
FROM Transactions t
WHERE

(

    t.transactionType IN (
        com.smartfinance.entities.Transactions.TransactionType.EXPENSE,
        com.smartfinance.entities.Transactions.TransactionType.TRANSFER_OUT
    )

    AND

    t.sender.id = :walletId
)

OR

(

    t.transactionType IN (
        com.smartfinance.entities.Transactions.TransactionType.DEPOSIT,
        com.smartfinance.entities.Transactions.TransactionType.TRANSFER_IN
    )

    AND

    t.receiver.id = :walletId
)

ORDER BY t.createdAt DESC
""")
    Page<Transactions> findUserTransactions(
            @Param("walletId") Long walletId,
            Pageable pageable
    );



    @Query("""
SELECT t
FROM Transactions t
WHERE

(

    (
        t.transactionType IN (
            com.smartfinance.entities.Transactions.TransactionType.EXPENSE,
            com.smartfinance.entities.Transactions.TransactionType.TRANSFER_OUT
        )

        AND

        t.sender.id = :walletId
    )

    OR

    (
        t.transactionType IN (
            com.smartfinance.entities.Transactions.TransactionType.DEPOSIT,
            com.smartfinance.entities.Transactions.TransactionType.TRANSFER_IN
        )

        AND

        t.receiver.id = :walletId
    )

    )
    
    AND
    
    t.transactionType = :type
    
    ORDER BY t.createdAt DESC
    """)
    Page<Transactions>
    findUserTransactionsByType(

            @Param("walletId")
            Long walletId,

            @Param("type")
            Transactions.TransactionType type,

            Pageable pageable
    );



}
