package com.lcwz.auth.Auth_App_Backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable(); //Disabling CSRF protection for simplicity, but in production, you should enable it
        http.authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                        .requestMatchers("/api/v1/auth/register").permitAll()//Everyone can access this endpoint without authentication
                        .requestMatchers("/api/v1/auth/login").permitAll() //Accessible without autrhentication
                        .anyRequest().authenticated() //Remaining endpoints require authentication
        )
                .httpBasic(Customizer.withDefaults()); //Enabling basic authentication

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService users(){
//        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//       UserDetails user1= userBuilder.username("zobi").password("abc").roles("ADMIN").build();
//       UserDetails user2= userBuilder.username("yuri").password("xyz").roles("USER").build();
//       UserDetails user3= userBuilder.username("vyom").password("pqr").roles("USER").build();
//
//       return new InMemoryUserDetailsManager(user1,user2,user3);
//
//    }

}
