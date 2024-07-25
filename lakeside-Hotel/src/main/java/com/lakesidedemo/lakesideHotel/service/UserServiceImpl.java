package com.lakesidedemo.lakesideHotel.service;

import com.lakesidedemo.lakesideHotel.exception.UserAlreadyExistsException;
import com.lakesidedemo.lakesideHotel.model.Role;
import com.lakesidedemo.lakesideHotel.model.User;
import com.lakesidedemo.lakesideHotel.repository.RoleRepository;
import com.lakesidedemo.lakesideHotel.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
 * @author : rabin
 */

/**
 * Service class for managing users.
 *
 * This class implements the IUserService interface and provides methods for
 * registering, retrieving, and deleting users.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    // Repositories and encoder required for user management
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    /**
     * Registers a new user.
     *
     * @param user The user to register.
     * @return The registered user.
     * @throws UserAlreadyExistsException if a user with the same email already exists.
     */
    @Override
    public User registerUser(User user) {
        // Check if a user with the given email already exists
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        // Encode the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Assign the default role to the user
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(userRole));

        // Save and return the registered user
        return userRepository.save(user);
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Deletes a user by email.
     *
     * @param email The email of the user to delete.
     */
    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email);
        if(theUser != null) {
            userRepository.deleteByEmail(email);
        }
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return The User object if found.
     * @throws UsernameNotFoundException if no user is found with the given email.
     */
    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)  // Find the user by email using the user repository
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));  // Throw exception if user not found
    }

}
