package com.lakesidedemo.lakesideHotel.service;

import com.lakesidedemo.lakesideHotel.model.User;

import java.util.List;

/*
 * @author : rabin
 */
public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
