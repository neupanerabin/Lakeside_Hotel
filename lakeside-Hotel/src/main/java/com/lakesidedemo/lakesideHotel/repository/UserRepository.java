package com.lakesidedemo.lakesideHotel.repository;

import com.lakesidedemo.lakesideHotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * @author : rabin
 */

/**
 * Repository interface for User entity.
 *
 * This interface extends JpaRepository to provide CRUD operations for the User entity.
 * It also defines custom query methods for additional operations.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Checks if a user exists by email.
     *
     * @param email The email to check.
     * @return True if a user with the given email exists, otherwise false.
     */
    boolean existsByEmail(String email);

    /**
     * Deletes a user by email.
     *
     * @param email The email of the user to delete.
     */
    void deleteByEmail(String email);

    /**
     * Finds a user by email.
     *
     * @param email The email of the user to find.
     * @return An Optional containing the found user, or empty if no user is found.
     */
    Optional<User> findByEmail(String email);
}
