package com.smartfinance.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProfileResponse {

    private String username;

    private String email;

    private BigDecimal balance;

    private Long walletId;

}