package com.smartfinance.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {

    private String message;
    private Object data;
    private boolean status;


    public ApiResponse(String message,
                       Object data,
                       boolean status){
        this.message = message;
        this.data = data;
        this.status = status;
    }
}
