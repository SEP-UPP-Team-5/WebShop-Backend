package com.example.webshop.service;

import com.example.webshop.dto.UserChangePassDto;
import com.example.webshop.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getUsers();
    boolean usernameExists(String username);
    User addUser(User user);
    Optional<User> findById(String id);
    User findByUsername(String username);
    User updateUser(User userWithNewData);
    void deleteById(String id);
    boolean changePassword(String id, UserChangePassDto userPasswordChangeDto);
}
