package com.productManagement.OrderService.Exception;

import com.productManagement.OrderService.External.Response.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomErrorResponse> ResponseExceptionHandler(CustomException exception){

        return new ResponseEntity<>(new CustomErrorResponse().builder()
                .errorMessage(exception.getMessage())
                .errorCode(exception.errorCode)
                .build(), HttpStatus.valueOf(exception.status));
}}
