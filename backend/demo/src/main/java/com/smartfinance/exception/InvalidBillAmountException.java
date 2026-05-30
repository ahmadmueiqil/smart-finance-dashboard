package com.smartfinance.exception;

public class InvalidBillAmountException extends RuntimeException{

    public InvalidBillAmountException(String message){
        super(message);
    }
}
