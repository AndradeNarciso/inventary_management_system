package com.andrade.inventary_management_system_backend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.exception.InvalidCredentialException;
import com.andrade.inventary_management_system_backend.exception.NameValueRequiredException;
import com.andrade.inventary_management_system_backend.exception.NotFoundException;

@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handlerGlobalException(Exception ex) {
        Response responseExeception = Response
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(responseExeception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleNotFoundException(NotFoundException ex) {
        Response responseNotFoundException = Response
                .builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(responseNotFoundException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<Response> handleInvalidCredentialException(InvalidCredentialException ex) {
        Response responseInvalidCredentialException = Response
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(responseInvalidCredentialException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NameValueRequiredException.class)
    public ResponseEntity<Response> handleNameValueRequiredException(NameValueRequiredException ex) {
        Response responseNameValueRequiredException = Response
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(responseNameValueRequiredException, HttpStatus.BAD_REQUEST);
    }

}

