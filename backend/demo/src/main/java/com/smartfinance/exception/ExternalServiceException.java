package com.smartfinance.exception;

public class ExternalServiceException extends RuntimeException{

    public ExternalServiceException(String message){
        super(message);
    }
}
