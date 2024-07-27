package com.lakesidedemo.lakesideHotel.security.user;

/*
 * @author : rabin
 */

import com.lakesidedemo.lakesideHotel.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom implementation of Spring Security's UserDetails interface.
 *
 * This class is used by Spring Security to represent user details in a way that can be used for authentication and authorization.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelUserDetails implements UserDetails {

    private Long id; // The unique identifier for the user.
    private String email; // The email address of the user.
    private String password; // The password of the user.
    private Collection<GrantedAuthority> authorities; // The roles or authorities granted to the user.

    /**
     * Builds a HotelUserDetails instance from a User object.
     *
     * @param user The User object from which to build the HotelUserDetails.
     * @return A HotelUserDetails instance populated with user data and authorities.
     */
    public static HotelUserDetails buildUserDetails(User user) {
        // Convert roles to authorities
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Create authorities from role names
                .collect(Collectors.toList());
        // Return a new HotelUserDetails instance with the user's data and authorities
        return new HotelUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    /**
     * Returns the authorities granted to the user.
     *
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the password of the user.
     *
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username (email) of the user.
     *
     * @return The user's email address.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired.
     *
     * @return true if the account is non-expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true; // In a real application, this might depend on user account status.
    }

    /**
     * Indicates whether the user's account is locked.
     *
     * @return true if the account is non-locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true; // In a real application, this might depend on user account status.
    }

    /**
     * Indicates whether the user's credentials (password) are expired.
     *
     * @return true if the credentials are non-expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // In a real application, this might depend on password expiration policies.
    }

    /**
     * Indicates whether the user's account is enabled.
     *
     * @return true if the account is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true; // In a real application, this might depend on user account status.
    }
}
