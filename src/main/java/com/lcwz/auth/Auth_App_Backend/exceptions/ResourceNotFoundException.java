package com.lcwz.auth.Auth_App_Backend.exceptions;

import jakarta.annotation.Resource;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(){

        super("Resource not found");
    }
}
