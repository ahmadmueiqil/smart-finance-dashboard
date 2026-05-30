package com.smartfinance.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyConvertRequest {

    private String fromCurrency;

    private String toCurrency;

    private BigDecimal amount;
}
