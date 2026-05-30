package com.smartfinance.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyConversionResponse {


    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private String targetCurrency;
    private Double exchangeRate;
}
