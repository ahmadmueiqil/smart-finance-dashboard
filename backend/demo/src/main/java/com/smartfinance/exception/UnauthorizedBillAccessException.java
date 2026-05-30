package com.smartfinance.exception;

public class UnauthorizedBillAccessException extends RuntimeException{

    public UnauthorizedBillAccessException(String message){
        super(message);
    }
}
