package com.lakesidedemo.lakesideHotel.controller;

import com.lakesidedemo.lakesideHotel.exception.UserAlreadyExistsException;
import com.lakesidedemo.lakesideHotel.model.User;
import com.lakesidedemo.lakesideHotel.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/*
 * @author : rabin
 */

/**
 * Controller class for handling user authentication.
 *
 * This class provides an endpoint for user registration.
 */
@RequiredArgsConstructor
public class AuthController {

    // Service for user-related operations
    private final IUserService userService;

    /**
     * Endpoint for registering a new user.
     *
     * @param user The user to be registered.
     * @return ResponseEntity with the registration status.
     */
    @PostMapping("register-user")
    public ResponseEntity<?> registerUser(User user) {
        try {
            userService.registerUser(user);  // Register the user using the user service
            return ResponseEntity.ok("Registration successful");  // Return success response
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());  // Return conflict response if user already exists
        }
    }
}
