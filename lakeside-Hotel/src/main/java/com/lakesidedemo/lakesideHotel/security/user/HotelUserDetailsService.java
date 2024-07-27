package com.lakesidedemo.lakesideHotel.security.user;

/*
 * @author : rabin
 */

import com.lakesidedemo.lakesideHotel.model.User;
import com.lakesidedemo.lakesideHotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of Spring Security's UserDetailsService interface.
 *
 * This service is responsible for loading user-specific data for authentication and authorization
 * based on the provided username (email).
 */
@Service
@RequiredArgsConstructor
public class HotelUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Repository to interact with the database for user data.

    /**
     * Loads user-specific data by username (email).
     *
     * @param email The email of the user to be loaded.
     * @return UserDetails object containing user information and authorities.
     * @throws UsernameNotFoundException If the user with the given email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch the user from the database using the repository.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Convert the User object to UserDetails and return it.
        return HotelUserDetails.buildUserDetails(user);
    }
}
