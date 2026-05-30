package com.smartfinance.dto;

import com.smartfinance.entities.Bill;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateBillRequest {


    @NotBlank(message = "The bill title is required")
    private String billTitle;

    @NotNull(message = "The amount is required")
    private BigDecimal amount;

    @NotNull(message = "the category is requiered")
    private Bill.BillType category;
}
