package com.gmalandrakis.restfulserviceexample.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class RestExceptionHandler { //TODO: Adjust controller and utilize

   /* @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> authError(AuthenticationException e) {
        return ResponseEntity.status(400).body("Unauthorized");
    }*/
}
