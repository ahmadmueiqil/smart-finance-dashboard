package com.smartfinance.services;


import com.smartfinance.dto.CurrencyConversionResponse;
import com.smartfinance.dto.CurrencyResponse;
import com.smartfinance.entities.User;
import com.smartfinance.entities.Wallet;
import com.smartfinance.exception.CurrencyNotSupportedException;
import com.smartfinance.exception.ExternalServiceException;
import com.smartfinance.exception.UserNotFoundException;
import com.smartfinance.externalAPIs.CurrencyClient;
import com.smartfinance.repositories.UserRepository;
import com.smartfinance.repositories.WalletRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import com.smartfinance.dto.CurrencyConvertRequest;
import com.smartfinance.dto.PopularCurrencyDto;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyClient client;



    private boolean isCurrencySupported(
            Map<String, Double> rates,
            String currency
    ) {

        return rates.containsKey(currency);

    }

    private BigDecimal convertCurrency(
            BigDecimal amount,
            Double rate
    ) {

        return amount.multiply(
                BigDecimal.valueOf(rate)
        );

    }


    public CurrencyConversionResponse convertCurrency(
            CurrencyConvertRequest request
    ) {

        try {

            String fromCurrency =
                    request.getFromCurrency()
                            .toUpperCase();

            String toCurrency =
                    request.getToCurrency()
                            .toUpperCase();

            CurrencyResponse response =
                    client.getRates(fromCurrency);

            if (!isCurrencySupported(
                    response.getRates(),
                    toCurrency
            )) {

                throw new CurrencyNotSupportedException(
                        "Currency not supported"
                );

            }

            Double exchangeRate =
                    response.getRates()
                            .get(toCurrency);

            BigDecimal convertedAmount =
                    convertCurrency(
                            request.getAmount(),
                            exchangeRate
                    );

            CurrencyConversionResponse dto =
                    new CurrencyConversionResponse();

            dto.setAmount(
                    request.getAmount()
            );

            dto.setConvertedAmount(
                    convertedAmount
            );

            dto.setTargetCurrency(
                    toCurrency
            );

            dto.setExchangeRate(
                    exchangeRate
            );

            return dto;

        } catch (FeignException ex) {

            throw new ExternalServiceException(
                    "Currency service unavailable"
            );

        }

    }

    public List<PopularCurrencyDto> getPopularCurrencies() {

        try {

            CurrencyResponse response =
                    client.getRates("USD");

            List<String> popularCurrencies =
                    List.of(
                            "EUR",
                            "GBP",
                            "JPY",
                            "JOD"
                    );

            return popularCurrencies.stream()
                    .map(currency -> {

                        PopularCurrencyDto dto =
                                new PopularCurrencyDto();

                        dto.setCode(currency);

                        dto.setRate(
                                response.getRates()
                                        .get(currency)
                        );

                        return dto;

                    })
                    .toList();

        } catch (FeignException ex) {

            throw new ExternalServiceException(
                    "Currency service unavailable"
            );

        }

    }
}
