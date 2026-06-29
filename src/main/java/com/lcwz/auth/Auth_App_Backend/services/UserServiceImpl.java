package com.lcwz.auth.Auth_App_Backend.services;

import com.lcwz.auth.Auth_App_Backend.entities.Provider;
import com.lcwz.auth.Auth_App_Backend.entities.User;
import com.lcwz.auth.Auth_App_Backend.dtos.UserDto;
import com.lcwz.auth.Auth_App_Backend.exceptions.ResourceNotFoundException;
import com.lcwz.auth.Auth_App_Backend.helpers.UserHelper;
import com.lcwz.auth.Auth_App_Backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServices {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("User with email " + userDto.getEmail() + " already exists");
        }
        //any extra checks can be placed
        //now we have to convert userDto to user entity and save it to the database
        //after saving we have to convert user entity to userDto and return it
        User user = modelMapper.map(userDto, User.class);
        user.setProvider(user.getProvider() != null ? user.getProvider() : Provider.LOCAL); // Set default provider if null
        //here we now want to assign role to new user for authoriation

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }


    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with given Email ID"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
    UUID user_id=UserHelper.parseUUID(userId); //validate UUID and convert to string by helper
        User existingUser = userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User not found with given ID"));
        // We are not going to change email id for this project.
        if (userDto.getName() != null)
            existingUser.setName(userDto.getName());

        if (userDto.getImage() != null)
            existingUser.setImage(userDto.getImage());

        if (userDto.getProvider() != null)
            existingUser.setProvider(userDto.getProvider());

        // TODO: Change password updation

        if (userDto.getPassword() != null)
            existingUser.setPassword(userDto.getPassword());

        existingUser.setEnable(userDto.isEnable());

        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDto.class);


    }

    @Override
    @Transactional //so that all the operations are performed and no partial operation is performed
    public void deleteUser(String userId) {
        UUID uId= UserHelper.parseUUID(userId); // Validate UUID format and convert to string
        //now find the user by id
        User user=userRepository.findById(uId).orElseThrow(() -> new ResourceNotFoundException("User not found with given ID"));
        //now delete the user
        userRepository.delete(user);

    }

    @Override
    public UserDto getUserById(String userId) {
        UUID uuid = UUID.fromString(userId);
        Optional<User> user_id= userRepository.findById(uuid);
        if (user_id.isPresent()) {
            User foundUser = user_id.get();
            // convert to DTO
            return modelMapper.map(foundUser, UserDto.class);
        } else {
            throw new RuntimeException("User not found");
        }

    }

    //Iterable use karne se we can pass lists and all
    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
