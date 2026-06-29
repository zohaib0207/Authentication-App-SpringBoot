package com.lcwz.auth.Auth_App_Backend.dtos;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String message,
        HttpStatus statusCode
) {
}
