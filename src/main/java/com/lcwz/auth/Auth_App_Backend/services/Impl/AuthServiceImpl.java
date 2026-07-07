package com.lcwz.auth.Auth_App_Backend.services.Impl;


import com.lcwz.auth.Auth_App_Backend.dtos.UserDto;
import com.lcwz.auth.Auth_App_Backend.services.AuthService;
import com.lcwz.auth.Auth_App_Backend.services.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserServices userServices;

    @Override
    public UserDto registerUser(UserDto userDto) {
        //any other logic like verifying email , role assignment and all can be done over here , here create user is just used to create user in database and for making sake only
       UserDto userDto1=  userServices.createUser(userDto);
        return userDto1;
    }

}
