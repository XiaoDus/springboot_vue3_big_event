package com.example.springboot_vue3.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private String code;

    public ServiceException(String code ,String msg){
        super(msg);
        this.code = code;
    }
}
