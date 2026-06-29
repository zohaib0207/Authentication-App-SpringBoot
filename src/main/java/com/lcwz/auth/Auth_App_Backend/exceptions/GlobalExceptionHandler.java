package com.lcwz.auth.Auth_App_Backend.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.lcwz.auth.Auth_App_Backend.dtos.ErrorResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice //This is used to handle exceptions globally in my project
public class GlobalExceptionHandler {
    //handling resource not found exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        com.lcwz.auth.Auth_App_Backend.dtos.ErrorResponse internalServerError = new com.lcwz.auth.Auth_App_Backend.dtos.ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(internalServerError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        com.lcwz.auth.Auth_App_Backend.dtos.ErrorResponse internalServerError = new com.lcwz.auth.Auth_App_Backend.dtos.ErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(internalServerError);
    }
}
