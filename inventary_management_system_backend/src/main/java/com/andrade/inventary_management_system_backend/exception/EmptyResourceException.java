package com.andrade.inventary_management_system_backend.exception;

public class EmptyResourceException extends RuntimeException {
    public EmptyResourceException(String message) {
        super(message);
    }
}
