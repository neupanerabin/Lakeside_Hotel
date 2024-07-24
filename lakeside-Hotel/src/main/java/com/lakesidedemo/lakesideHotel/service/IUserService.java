package com.lakesidedemo.lakesideHotel.service;

import com.lakesidedemo.lakesideHotel.model.User;

import java.util.List;

/*
 * @author : rabin
 */

/**
 * Service interface for managing users.
 *
 * This interface defines the methods for user-related operations such as
 * registering a user, retrieving users, deleting a user, and fetching a user by email.
 */
public interface IUserService {

    /**
     * Registers a new user.
     *
     * @param user The user to register.
     * @return The registered user.
     */
    User registerUser(User user);

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    List<User> getUsers();

    /**
     * Deletes a user by email.
     *
     * @param email The email of the user to delete.
     */
    void deleteUser(String email);

    /**
     * Retrieves a user by email.
     *
     * @param email The email of the user to retrieve.
     * @return The user with the given email.
     */
    User getUser(String email);
}
