package com.lakesidedemo.lakesideHotel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * @author : rabin
 */

/**
 * Configuration class for security-related beans.
 * This class defines the beans required for the security configuration of the application.
 */
@Configuration
public class HotelSecurityConfig {

    /**
     * Bean definition for PasswordEncoder.
     * This bean provides a BCryptPasswordEncoder that can be used to encode passwords
     * for secure storage and comparison.
     *
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Returns a new instance of BCryptPasswordEncoder
    }
}
