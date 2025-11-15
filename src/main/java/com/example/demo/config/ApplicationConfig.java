package com.example.demo.config;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;

    ApplicationConfig(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
