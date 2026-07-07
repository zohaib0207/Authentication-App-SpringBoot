package com.lcwz.auth.Auth_App_Backend.services;

import com.lcwz.auth.Auth_App_Backend.dtos.UserDto;


//This will contain the things related to authentication like login, register, forgot password, reset password etc
public interface AuthService {
    UserDto registerUser(UserDto userDto);

    //Login User

}
