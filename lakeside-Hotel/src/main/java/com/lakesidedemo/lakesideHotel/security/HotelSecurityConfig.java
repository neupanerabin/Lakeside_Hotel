package com.lakesidedemo.lakesideHotel.security;

// Declares the package for the class, organizing the code within the project structure.

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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// Marks the class as a source of bean definitions for the application context.

@RequiredArgsConstructor
// Generates a constructor with required arguments (final fields) using Lombok.

@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
// Enables method security in the application, allowing the use of security annotations on methods.

public class HotelSecurityConfig {

    private final HotelUserDetailsService userDetailsService;
    // Injects the custom user details service to manage user authentication.

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    // Injects the custom authentication entry point for handling unauthorized access.

    @Bean
    public AuthTokenFilter authenticationTokenFilter() {
        return new AuthTokenFilter();
        // Defines a bean for the JWT authentication token filter.
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // Defines a bean for password encoding using BCrypt.
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        // Sets the custom user details service for the authentication provider.

        authProvider.setPasswordEncoder(passwordEncoder());
        // Sets the password encoder for the authentication provider.

        return authProvider;
        // Returns the configured authentication provider.
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
        // Defines a bean for the authentication manager, obtaining it from the authentication configuration.
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // Disables CSRF protection, which is often necessary for stateless APIs.

                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
                // Configures exception handling, setting the custom JWT authentication entry point for unauthorized access.

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configures session management to be stateless, as JWT is used for authentication.

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/rooms/**").permitAll()
                        // Allows public access to endpoints under /auth/** and /rooms/**.

                        .requestMatchers("/roles/**").hasRole("ADMIN")
                        // Restricts access to endpoints under /roles/** to users with the ADMIN role.

                        .anyRequest().authenticated());
        // Requires authentication for all other requests.

        http.authenticationProvider(authenticationProvider());
        // Sets the custom authentication provider.

        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // Adds the JWT authentication token filter before the default username/password authentication filter.

        return http.build();
        // Builds and returns the configured security filter chain.
    }

}
