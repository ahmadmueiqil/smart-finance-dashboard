package com.smartfinance.Controllers;


import com.smartfinance.dto.ApiResponse;
import com.smartfinance.dto.CurrencyConversionResponse;
import com.smartfinance.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.smartfinance.dto.CurrencyConvertRequest;
import com.smartfinance.dto.PopularCurrencyDto;

import java.util.List;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("/convert")
    public ResponseEntity<ApiResponse<CurrencyConversionResponse>> convertCurrency(
            @RequestBody CurrencyConvertRequest request){

        CurrencyConversionResponse response =
                currencyService.convertCurrency(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Currency converted successfully",
                        response,
                        true
                )
        );
    }



    @GetMapping("/popular")
    public ResponseEntity<ApiResponse<List<PopularCurrencyDto>>> getPopularCurrencies() {

        List<PopularCurrencyDto> currencies =
                currencyService.getPopularCurrencies();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        "Popular currencies fetched successfully",
                        currencies,
                        true
                )
        );

    }

}
