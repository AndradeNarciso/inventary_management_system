package com.andrade.inventary_management_system_backend.exception;

public class RequiredRoleException extends RuntimeException {
    public RequiredRoleException(String message) {
        super(message);
    }
}
