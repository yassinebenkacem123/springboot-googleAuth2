package com.example.server.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.server.payload.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler{
    // validation Exception : 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentsValidation(MethodArgumentNotValidException e){
        Map<String, String> response = new HashMap<>();
        e.getAllErrors().forEach(err -> {
            String fieldName = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldName, message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // resource not found exception : 
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> resourceNotFoundMethod(ResourceNotFoundException e){
        String message = e.getMessage();
        APIResponse apiResponse  = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setStatus(false);
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    
    // Api execption : for bad request.
    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> apiException(APIException e){
        String message = e.getMessage();
        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(message);
        apiResponse.setStatus(false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);

    }
}
