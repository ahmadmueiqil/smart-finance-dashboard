//package com.smartfinance.services;
//
//
//import com.smartfinance.dto.CurrencyConversionResponse;
//import com.smartfinance.dto.CurrencyResponse;
//import com.smartfinance.entities.User;
//import com.smartfinance.entities.Wallet;
//import com.smartfinance.exception.CurrencyNotSupportedException;
//import com.smartfinance.exception.ExternalServiceException;
//import com.smartfinance.exception.UserNotFoundException;
//import com.smartfinance.externalAPIs.CurrencyClient;
//import com.smartfinance.repositories.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.core.Authentication;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class CurrencyServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private CurrencyClient client;
//
//    @Mock
//    private WalletService walletService;
//
//    @InjectMocks
//    private CurrencyService currencyService;
//
//
//    @Test
//    void getConvertedBalance_shouldReturnConvertedBalanceSuccessfully(){
//
//        //Arrange
//        Authentication authentication = mock(Authentication.class);
//
//        String targetCurrency = "EUR";
//
//        Map<String, Double> map = new HashMap<>();
//        map.put(targetCurrency, 0.9);
//
//        CurrencyResponse response = new CurrencyResponse();
//        response.setBase("USD");
//        response.setRates(map);
//
//
//        when(walletService.getBalance(authentication))
//                .thenReturn(new BigDecimal("100"));
//        when(client.getRates(response.getBase()))
//                .thenReturn(response);
//
//        //Act
//        CurrencyConversionResponse conversionResponse = currencyService.getConvertedBalance(authentication,targetCurrency);
//
//        //Assert
//        assertTrue(conversionResponse.getTargetCurrency().equals(targetCurrency));
//        assertTrue(conversionResponse.getOriginalBalance().compareTo(new BigDecimal("100")) == 0);
//        assertTrue(conversionResponse.getConvertedBalance().compareTo(new BigDecimal("90")) == 0);
//
//
//    }
//
//
//    @Test
//    void getConvertedBalance_shouldThrowsExceptionWhenUnsupportedCurrency(){
//        //Arrange
//        Authentication authentication = mock(Authentication.class);
//
//        String targetCurrency = "JOR";
//
//        Map<String, Double> map = new HashMap<>();
//        map.put("EUR", 0.9);
//
//        CurrencyResponse response = new CurrencyResponse();
//        response.setBase("USD");
//        response.setRates(map);
//
//
//        when(client.getRates(response.getBase()))
//                .thenReturn(response);
//
//        //Act + Assert
//        assertThrows(CurrencyNotSupportedException.class,
//                () -> currencyService.getConvertedBalance(authentication,targetCurrency));
//    }
//
//    @Test
//    void getConvertedBalance_shouldThrowsExceptionWhenExternalAPIFaliarAccure(){
//
//        //Arrange
//        Authentication authentication = mock(Authentication.class);
//
//        String targetCurrency = "EUR";
//
//        Map<String, Double> map = new HashMap<>();
//        map.put(targetCurrency, 0.9);
//
//        CurrencyResponse response = new CurrencyResponse();
//        response.setBase("USD");
//        response.setRates(map);
//
//        when(client.getRates(response.getBase()))
//                .thenThrow(ExternalServiceException.class);
//
//        //Act
//        assertThrows(ExternalServiceException.class,
//                () -> currencyService.getConvertedBalance(authentication,targetCurrency));
//    }
//
//    @Test
//    void getConvertedBalance_shouldThrowExceptionWhenUserNotFound() {
//        //Arrange
//        Authentication authentication = mock(Authentication.class);
//
//
//        String targetCurrency = "EUR";
//
//        Map<String, Double> map = new HashMap<>();
//        map.put(targetCurrency, 0.9);
//
//        CurrencyResponse response = new CurrencyResponse();
//        response.setBase("USD");
//        response.setRates(map);
//
//
//        when(walletService.getBalance(authentication))
//                .thenThrow(UserNotFoundException.class);
//        when(client.getRates(response.getBase()))
//                .thenReturn(response);
//
//        //Act
//        assertThrows(UserNotFoundException.class,
//                () -> currencyService.getConvertedBalance(authentication,targetCurrency));
//    }
//
//}
