package com.lcwz.auth.Auth_App_Backend.Security;

import com.lcwz.auth.Auth_App_Backend.entities.User;
import com.lcwz.auth.Auth_App_Backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service //so that iski bean available hojaye and yahi use ho fir
@RequiredArgsConstructor //so that final userrepository ka constructor ban jaye automatically
public class CustomUserDetailService implements UserDetailsService {
    //This is the method that will load the data from the database and return the user details
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //now we will fetch from DB using the userRepository
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return user;
    }
}
