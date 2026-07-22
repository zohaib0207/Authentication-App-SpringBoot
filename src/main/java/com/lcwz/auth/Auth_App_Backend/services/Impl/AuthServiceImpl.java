package com.lcwz.auth.Auth_App_Backend.services.Impl;


import com.lcwz.auth.Auth_App_Backend.dtos.UserDto;
import com.lcwz.auth.Auth_App_Backend.services.AuthService;
import com.lcwz.auth.Auth_App_Backend.services.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserDto userDto) {
        //any other logic like verifying email , role assignment and all can be done over here , like currently create user is just being made for making the user as a dummy user, we later can fetch data from actual DB

        //Before giving the password to user DTO we will encode the password and then pass the details
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); //now the data is stored in encoded format
       UserDto userDto1=  userServices.createUser(userDto);
        return userDto1;
    }

}
