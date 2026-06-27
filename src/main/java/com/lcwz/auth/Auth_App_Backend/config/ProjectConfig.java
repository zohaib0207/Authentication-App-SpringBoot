package com.lcwz.auth.Auth_App_Backend.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
