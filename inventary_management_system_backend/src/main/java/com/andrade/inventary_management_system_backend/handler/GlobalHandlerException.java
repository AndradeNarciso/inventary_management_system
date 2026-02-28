package com.andrade.inventary_management_system_backend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.andrade.inventary_management_system_backend.dto.Response;
import com.andrade.inventary_management_system_backend.exception.DuplicatedValueException;
import com.andrade.inventary_management_system_backend.exception.HeaderNotFoundException;
import com.andrade.inventary_management_system_backend.exception.InvalidCredentialException;
import com.andrade.inventary_management_system_backend.exception.InvalidMonthException;
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

    @ExceptionHandler(HeaderNotFoundException.class)
    public ResponseEntity<Response> handlerHeaderNotFoundException(HeaderNotFoundException ex) {
        Response responseHeaderNotFoundException = Response
                .builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(responseHeaderNotFoundException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidMonthException.class)
    public ResponseEntity<Response> handlerInvalidMonthException(InvalidMonthException ex) {
        Response responseHeaderInvalidMonthException = Response
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(responseHeaderInvalidMonthException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Response responseMethodArgumentNotValidException = Response
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("There is Field required")
                .build();

        return new ResponseEntity<>(responseMethodArgumentNotValidException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Response responseHttpMessageNotReadableException = Response
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Invalid input format. Check the fields sent")
                .build();

        return new ResponseEntity<>(responseHttpMessageNotReadableException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedValueException.class)
    public ResponseEntity<Response> handlerDuplicatedValueException(DuplicatedValueException ex) {
        Response responseDuplicatedValueException = Response
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(responseDuplicatedValueException, HttpStatus.BAD_REQUEST);
    }
}
