package com.customer.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(NotFoundException e){
        Map<String, String> hm = new HashMap<>();
        hm.put("errorMessage", e.getMessage());
        return hm;
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAppException(AppException e){
        Map<String, String> hm = new HashMap<>();
        hm.put("errorMessage", e.getMessage());
        return hm;
    }
}
