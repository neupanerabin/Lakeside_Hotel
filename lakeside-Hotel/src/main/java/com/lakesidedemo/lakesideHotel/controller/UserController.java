package com.lakesidedemo.lakesideHotel.controller;

import com.lakesidedemo.lakesideHotel.model.User;
import com.lakesidedemo.lakesideHotel.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * @author : rabin
 */

/**
 * REST controller for managing users.
 *
 * This controller provides endpoints for retrieving all users, fetching a user by email,
 * and deleting a user by email.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    // Service for user-related operations
    private final IUserService userService;

    /**
     * Endpoint to get all users.
     *
     * @return A response entity containing a list of all users and the HTTP status.
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.FOUND);
    }

    /**
     * Endpoint to get a user by email.
     *
     * @param email The email of the user to retrieve.
     * @return A response entity containing the user and the HTTP status, or an error message if not found.
     */
    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
        try {
            // Fetch the user by email
            User theUser = userService.getUser(email);
            return ResponseEntity.ok(theUser);
        } catch (UsernameNotFoundException e) {
            // Handle case where user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Fetching User");
        }
    }

    /**
     * Endpoint to delete a user by email.
     *
     * @param email The email of the user to delete.
     * @return A response entity containing a success message or an error message if the user is not found.
     */
    @GetMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and #email == principal.username)")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String email) {
        try {
            // Delete the user by email
            userService.deleteUser(email);
            return ResponseEntity.ok("User deleted successfully");
        } catch (UsernameNotFoundException e) {
            // Handle case where user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting User");
        }
    }
}
