package com.lcwz.auth.Auth_App_Backend.exceptions;

import jakarta.annotation.Resource;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(){

        super("Resource not found");
    }

    //Need to add user not found exception as a error to be caught in this? - airport commits can be ignored
}
