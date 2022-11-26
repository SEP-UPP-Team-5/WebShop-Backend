package com.example.webshop.dto.mapper;

import com.example.webshop.dto.AddUserDto;
import com.example.webshop.dto.UserDto;
import com.example.webshop.model.Address;
import com.example.webshop.model.Gender;
import com.example.webshop.model.Role;
import com.example.webshop.model.User;

import java.util.UUID;

public class UserMapper {
    public UserDto UserToUserDto(User user){
        Address address = user.getAddress();
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getEmail(),
                user.getTelephoneNo(), user.getGender().toString(), user.getDateOfBirth(), address.getStreet(),
                address.getStreetNumber(), address.getCity(), address.getPostalCode(), user.getRole().toString());
    }

    public User AddUserDtoToUser(AddUserDto userDto){
        return new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getName(),
                userDto.getEmail(), userDto.getTelephoneNo(), Gender.valueOf(userDto.getGender()), userDto.getDateOfBirth(),
                new Address(UUID.randomUUID().toString(), userDto.getStreet(), userDto.getStreetNumber(), userDto.getCity(), userDto.getPostalCode(),
                        "Serbia", 12.3, 12.3), Role.valueOf(userDto.getRole()));
    }
}
