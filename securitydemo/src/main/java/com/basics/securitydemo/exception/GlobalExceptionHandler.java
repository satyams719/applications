package com.basics.securitydemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleTimeOutException(HttpClientErrorException ex){
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request time out, Please try again later");
    }
}
