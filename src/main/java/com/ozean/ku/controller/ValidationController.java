package com.ozean.ku.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ValidationController {

    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public String error(Exception e){
        if(e instanceof ConstraintViolationException exception) {
            return exception.getMessage();
        } else if(e instanceof MethodArgumentNotValidException exception){
            if (exception.getFieldError() == null) return "未知错误";
            return exception.getFieldError().getDefaultMessage();
        }
        return "未知错误";
    }
}
