package com.example.springsecuritylearning.security;

import org.springframework.beans.PropertyBatchUpdateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PassWordConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new  BCryptPasswordEncoder(10);

    }
}
