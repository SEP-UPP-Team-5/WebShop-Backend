package com.example.webshop.service.impl;

import com.example.webshop.model.User;
import com.example.webshop.repository.UserRepository;
import com.example.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    @Override
    public User addUser(User user) {
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        return user;
    }
}
