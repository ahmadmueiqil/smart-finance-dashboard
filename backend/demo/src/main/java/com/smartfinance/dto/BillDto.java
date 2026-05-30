package com.smartfinance.dto;


import com.smartfinance.entities.Bill;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BillDto {

    private Long id;
    private Bill.BillType billType;
    private Bill.Status status;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private String title;

}
