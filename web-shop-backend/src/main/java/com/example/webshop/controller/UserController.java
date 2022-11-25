package com.example.webshop.controller;

import com.example.webshop.dto.AddUserToDto;
import com.example.webshop.dto.UserDto;
import com.example.webshop.dto.mapper.UserMapper;
import com.example.webshop.model.User;
import com.example.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    final private UserMapper userMapper = new UserMapper();

    @GetMapping
    public List<UserDto> getUsers(){
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userService.getUsers())
            userDtoList.add(userMapper.UserToUserDto(user));
        return userDtoList;
    }

    @PostMapping
    public String saveUser(@RequestBody AddUserToDto userDto){
        if(isNullOrEmpty(userDto.getUsername(), userDto.getPassword(), userDto.getName(), userDto.getEmail(), userDto.getTelephoneNo(), userDto.getGender(), userDto.getDateOfBirth().toString(),
                userDto.getStreet(), userDto.getStreetNumber(), userDto.getCity(), userDto.getPostalCode()))
            return "None of fields cannot be empty!";
        if(userService.usernameExists(userDto.getUsername()))
            return "Username already exists!";
        return "Added user with id " + userService.addUser(userMapper.AddUserDtoToUser(userDto)).getId();
    }

    private static boolean isNullOrEmpty(String... strArr){
        for (String st : strArr) {
            if  (st==null || st.equals(""))
                return true;

        }
        return false;
    }
}



