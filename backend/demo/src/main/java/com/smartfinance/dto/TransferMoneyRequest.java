package com.smartfinance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferMoneyRequest {

    @NotNull(message = "To wallet id is requiered")
    private Long toWalletId;


    @NotNull(message = "The ammount is requiered")
    private BigDecimal amount = BigDecimal.ZERO;

}
