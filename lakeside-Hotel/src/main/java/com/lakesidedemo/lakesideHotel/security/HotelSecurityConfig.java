package com.lakesidedemo.lakesideHotel.security;

import com.lakesidedemo.lakesideHotel.security.jwt.AuthTokenFilter;
import com.lakesidedemo.lakesideHotel.security.jwt.JwtAuthEntryPoint;
import com.lakesidedemo.lakesideHotel.security.user.HotelUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 * @author : rabin
 */

/**
 * Configuration class for security-related beans.
 * This class defines the beans required for the security configuration of the application.
 */
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class HotelSecurityConfig {

    private final HotelUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    /**
     * Bean definition for the authentication token filter.
     * This filter intercepts requests and checks for valid JWT tokens.
     *
     * @return a new instance of AuthTokenFilter.
     */
    @Bean
    public AuthTokenFilter authenticationTokenFilter() {
        return new AuthTokenFilter();
    }

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

    /**
     * Bean definition for DaoAuthenticationProvider.
     * This provider is responsible for authenticating users based on their username and password.
     *
     * @return a configured DaoAuthenticationProvider instance.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Bean definition for AuthenticationManager.
     * This manager handles authentication requests and delegates them to the appropriate provider.
     *
     * @param authConfig the authentication configuration.
     * @return an AuthenticationManager instance.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Security filter chain configuration.
     * This method configures the HTTP security for the application, setting up JWT authentication,
     * session management, and request authorization.
     *
     * @param http the HttpSecurity configuration.
     * @return a configured SecurityFilterChain instance.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Disable CSRF protection
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))  // Handle authentication exceptions
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Use stateless session management
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()  // Allow public access to authentication endpoints
                        .anyRequest().authenticated());  // Require authentication for all other requests
        http.authenticationProvider(authenticationProvider());  // Set the authentication provider
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);  // Add JWT filter before the username/password filter
        return http.build();
    }
}
