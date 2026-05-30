package com.smartfinance.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BillPaymentRequest {

    @NotNull(message = "Bill id is requiered")
    private Long billId;
}
