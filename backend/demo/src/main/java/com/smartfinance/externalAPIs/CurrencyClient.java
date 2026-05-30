package com.smartfinance.externalAPIs;


import com.smartfinance.dto.CurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "currency-client", url = "https://api.exchangerate-api.com")
public interface CurrencyClient {



    @GetMapping("/v4/latest/{base}")
    CurrencyResponse getRates(@PathVariable String base);
}
