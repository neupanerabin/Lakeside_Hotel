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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Override
    public User registerUser(User user) {
        if(userRepository.existByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(userRole));
        
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);

    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
