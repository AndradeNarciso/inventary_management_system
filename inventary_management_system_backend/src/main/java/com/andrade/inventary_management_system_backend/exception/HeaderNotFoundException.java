package com.andrade.inventary_management_system_backend.exception;

public class HeaderNotFoundException extends RuntimeException {
    public HeaderNotFoundException(String message){
        super(message);
    }
}
