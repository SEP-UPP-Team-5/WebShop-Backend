package com.example.webshop.service;

import com.example.webshop.model.User;

import java.util.List;
public interface UserService {
    List<User> getUsers();
    boolean usernameExists(String username);
    User addUser(User user);
}
