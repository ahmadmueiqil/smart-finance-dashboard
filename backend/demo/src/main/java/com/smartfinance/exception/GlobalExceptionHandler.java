package com.smartfinance.exception;


import com.smartfinance.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiResponse<Object>> handleInsufficientBalanceException(InsufficientBalanceException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(
                                ex.getMessage(),
                                List.of(),
                                false
                        )
                );
    }

    @ExceptionHandler(BillAlreadyPaidException.class)
    public ResponseEntity<ApiResponse<Object>> handleBillAlreadyPaidException(BillAlreadyPaidException ex){

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(
                                ex.getMessage(),
                                List.of(),
                                false
                        )
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ApiResponse<>(
                                ex.getMessage(),
                                List.of(),
                                false
                        )
                );
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleWalletNotFoundException(WalletNotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(
                                ex.getMessage(),
                                List.of(),
                                false
                        )
                );
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ApiResponse<Object>> handelInvalidAmountException(InvalidAmountException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(
                                ex.getMessage(),
                                List.of(),
                                false
                        )
                );
    }

    @ExceptionHandler(CurrencyNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handelCurrencyNotSupportedException(CurrencyNotSupportedException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(ex.getMessage(),
                                List.of(),
                                false)
                );
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ApiResponse<Object>> handelExternalServiceException(ExternalServiceException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(ex.getMessage(),
                                List.of(),
                                false)
                );
    }

    @ExceptionHandler(BillNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleBillNotFoundException(BillNotFoundException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(ex.getMessage(),
                                List.of(),
                                false)
                );
    }

    @ExceptionHandler(InvalidEamilOrPasswordException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidEamilOrPasswordException(InvalidEamilOrPasswordException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(ex.getMessage(),
                                List.of(),
                                false)
                );
    }


    @ExceptionHandler(InvalidBillAmountException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidBillAmountException(InvalidBillAmountException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(ex.getMessage(),
                                List.of(),
                                false)
                );
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(
                                ex.getMessage(),
                                List.of(),
                                false
                        )
                );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Validation Error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ApiResponse<>(
                                message,
                                List.of(),
                                false
                        )
                );
    }

}
