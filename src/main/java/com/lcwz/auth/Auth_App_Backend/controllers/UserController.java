package com.lcwz.auth.Auth_App_Backend.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lcwz.auth.Auth_App_Backend.dtos.UserDto;
import com.lcwz.auth.Auth_App_Backend.services.UserServices;


@RestController //because we are creating a REST API
@RequestMapping("/api/v1/users") //base url for all the endpoints in this controller
@AllArgsConstructor
public class UserController {
    private final UserServices userService;
    //A method that creates user at POST request - API to create user
    @PostMapping
    public ResponseEntity<UserDto>createUser(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));

    }

    //API to get all users
    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //API to get user by id
    @GetMapping ("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
