package com.hms.exceptions;


import com.hms.dto.ApiResponse;
import org.hibernate.NonUniqueResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){

        String message = ex.getMessage();

        ApiResponse response =  ApiResponse.builder().message(message).success(TRUE).status(HttpStatus.NOT_FOUND).build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handlemethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> map = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->
        {
            String fieldName = ((FieldError)error).getField();
            String message= error.getDefaultMessage();
            map.put(fieldName, message);

        });

        return new ResponseEntity<Map<String,String >>(map,HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(NonUniqueResultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleUniqueConstraintException(UniqueConstraintException ex) {
         return ApiResponse.builder().message(ex.getMessage()).success(FALSE).status(HttpStatus.BAD_REQUEST).build();
    }


}

