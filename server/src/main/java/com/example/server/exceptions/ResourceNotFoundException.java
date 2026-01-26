package com.example.server.exceptions;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String field,String fieldValue ){
        super(String.format("%s not found with %s: %d",resourceName, field, fieldValue)); // message.

    }
    
    public ResourceNotFoundException(String message){
        super(message);
    }
    
}
