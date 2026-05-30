package com.smartfinance.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class CurrencyResponse {

    private String base;
    private Map<String, Double> rates;
}
