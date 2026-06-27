package com.lcwz.auth.Auth_App_Backend.services;


import com.lcwz.auth.Auth_App_Backend.dtos.UserDto;

import java.util.UUID;

public interface UserServices {
    //create User
    UserDto createUser(UserDto userDto);

    //get User by email
    UserDto getUserByEmail(String email);

    //update User
    UserDto updateUser(UserDto userDto , String userId);

    //delete User
    void deleteUser(String userId);

    //get User by id
    UserDto getUserById(String userId);

    //get all Users
    Iterable<UserDto> getAllUsers();

    //user service se related methods are put here
}
