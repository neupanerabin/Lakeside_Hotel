package com.lakesidedemo.lakesideHotel.controller;

// Declares the package for the class, organizing the code within the project structure.

import com.lakesidedemo.lakesideHotel.exception.UserAlreadyExistsException;
import com.lakesidedemo.lakesideHotel.model.User;
import com.lakesidedemo.lakesideHotel.request.LoginRequest;
import com.lakesidedemo.lakesideHotel.response.JwtResponse;
import com.lakesidedemo.lakesideHotel.security.jwt.JwtUtils;
import com.lakesidedemo.lakesideHotel.security.user.HotelUserDetails;
import com.lakesidedemo.lakesideHotel.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @author : rabin
 */

/**
 * Controller class for handling user authentication.
 *
 * This class provides endpoints for user registration and login.
 */
@RequiredArgsConstructor
// Generates a constructor with required arguments (final fields) using Lombok.
@RestController
@RequestMapping("/auth")
public class AuthController {

    // Service for user-related operations
    private final IUserService userService;
    // Injects the user service to handle user registration and other user-related operations.

    private final AuthenticationManager authenticationManager;
    // Injects the authentication manager to handle user authentication.

    private final JwtUtils jwtUtils;
    // Injects the JWT utility class for generating and validating JWT tokens.

    /**
     * Endpoint for registering a new user.
     *
     * @param user The user to be registered.
     * @return ResponseEntity with the registration status.
     */
    @PostMapping("/register-user")
    // Maps HTTP POST requests to /register-user to this method.
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Method to register a new user.
        try {
            userService.registerUser(user);
            // Register the user using the user service.

            return ResponseEntity.ok("Registration successful");
            // Return success response if registration is successful.
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            // Return conflict response if the user already exists.
        }
    }

    /**
     * Endpoint for user authentication.
     *
     * @param loginRequest The login request containing email and password.
     * @return ResponseEntity with the JWT token and user details.
     */
    @PostMapping("/login")
    // Maps HTTP POST requests to /login to this method.

    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Method to authenticate a user and generate a JWT token.

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        // Authenticate the user using the authentication manager with email and password.

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Set the authentication in the security context.

        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        // Generate a JWT token for the authenticated user.

        HotelUserDetails userDetails = (HotelUserDetails) authentication.getPrincipal();
        // Get the user details from the authentication object.

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        // Extract the user roles from the user details.

        return ResponseEntity.ok(new JwtResponse(
                userDetails.getId(),
                userDetails.getEmail(),
                jwt,
                roles));
        // Return the JWT token and user details in the response.
    }
}
