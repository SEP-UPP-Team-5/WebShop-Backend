package com.example.webshop.service.impl;

import com.example.webshop.dto.UserChangePassDto;
import com.example.webshop.model.User;
import com.example.webshop.repository.UserRepository;
import com.example.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User userWithNewData) {
        Optional<User> user = findById(userWithNewData.getId());
        return userRepository.save(userWithNewData);
    }
    @Override
    public boolean changePassword(String id, UserChangePassDto userPasswordChangeDto) {
        Optional<User> result = userRepository.findById(id);

        if(!result.isPresent()) return false;

        User user = result.get();

        if(!user.getUsername().equals(userPasswordChangeDto.getUsername())
                || !BCrypt.checkpw(userPasswordChangeDto.getOldPassword(), user.getPassword())){
            return false;
        }

        String password = userPasswordChangeDto.getPassword();
        if (!userPasswordChangeDto.getPassword().equals("")) {
            password = BCrypt.hashpw(userPasswordChangeDto.getPassword(), BCrypt.gensalt());
        }

        user.setPassword(password);

        userRepository.save(user);

        return true;
    }
}
