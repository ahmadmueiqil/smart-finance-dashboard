package com.smartfinance.exception;

public class InvalidEamilOrPasswordException extends RuntimeException{
    public InvalidEamilOrPasswordException(String massege){
        super(massege);
    }
}
