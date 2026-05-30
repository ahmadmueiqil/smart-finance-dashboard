package com.smartfinance.Controllers;


import com.smartfinance.dto.*;
import com.smartfinance.entities.Bill;
import com.smartfinance.entities.Transactions;
import com.smartfinance.services.TransactionService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TransactionDto>>> getAllTransactions(Authentication authentication,
                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                @RequestParam(required = false) Transactions.TransactionType type){

        Page<TransactionDto> transactions = transactionService.getAllTransactions(authentication, page, size, type);

        return ResponseEntity.ok(
                new ApiResponse<>("Fetch successfuly",
                        transactions,
                        true)
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<Object>> transferMoney(@Valid @RequestBody TransferMoneyRequest request,
                                                             Authentication authentication){

        transactionService.transferMoney(request,
                authentication);

        return ResponseEntity.ok(
                new ApiResponse<>("Money transfered",
                        null,
                        true)
        );

    }

}
