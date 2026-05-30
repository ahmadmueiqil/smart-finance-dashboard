package com.smartfinance.dto;


import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
@Data
public class DashboardDto {

    private BigDecimal balance;
    private BigDecimal totalExpanses;
    private BigDecimal totalTransferIn;
    private BigDecimal totalTransferOut;
    private int pindingBills;
    private Page<TransactionDto> lastTransactions;
    private Page<BillDto> lastBills;


}
