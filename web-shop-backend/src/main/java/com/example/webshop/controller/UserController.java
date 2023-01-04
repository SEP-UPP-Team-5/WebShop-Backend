package com.example.webshop.controller;

import com.example.webshop.dto.*;
import com.example.webshop.dto.mapper.UserMapper;
import com.example.webshop.model.User;
import com.example.webshop.service.UserService;
import com.example.webshop.token.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    final private UserMapper userMapper = new UserMapper();

    @GetMapping
    public List<UserDto> getUsers(){
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userService.getUsers())
            userDtoList.add(userMapper.UserToUserDto(user));
        return userDtoList;
    }
    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody AddUserDto userDto){
        if(isNullOrEmpty(userDto.getUsername(), userDto.getPassword(), userDto.getName(), userDto.getEmail(), userDto.getTelephoneNo(), userDto.getGender(), userDto.getDateOfBirth().toString(),
                userDto.getStreet(), userDto.getStreetNumber(), userDto.getCity(), userDto.getPostalCode()))
            return new ResponseEntity<>("None of fields cannot be empty!", HttpStatus.BAD_REQUEST);
        if(userService.usernameExists(userDto.getUsername()))
            return new ResponseEntity<>("Username already exists!", HttpStatus.BAD_REQUEST);

        User user = userMapper.AddUserDtoToUser(userDto);
        String encodedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
        user.setPassword(encodedPassword);

        return new ResponseEntity<>("Added user with id " + userService.addUser(user).getId(), HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthDto dto) throws  NoSuchAlgorithmException{
        User user = userService.findByUsername(dto.getUsername());
        if(user == null)
            return new ResponseEntity<>("Invalid username/password!", HttpStatus.BAD_REQUEST);
        else{
            if(BCrypt.checkpw(dto.getPassword(), user.getPassword())){

                return new ResponseEntity<>(new TokenDTO(jwtTokenUtil.generateToken(user.getUsername()), user.getUsername()), HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Invalid username/password!", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getOne(@PathVariable String id){
        Optional<User> user = userService.findById(id);
        if(user.isPresent()) {
            return new ResponseEntity<>(userMapper.UserToUserDto(user.get()), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/username/{username}")
    public UserDto getOneByUsername(@PathVariable String username){
        return userMapper.UserToUserDto(userService.findByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        Optional<User> user = userService.findById(id);
        if(!user.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userService.deleteById(id);
        return new ResponseEntity<>("User deleted with id " + id, HttpStatus.OK);
    }

    @PutMapping(value="/pass/{id}")
    public ResponseEntity<Void> changePassword(
            @PathVariable String id,
            @RequestBody UserChangePassDto dto){

        if(!dto.getPassword().equals(dto.getPasswordConfirm())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        boolean result = userService.changePassword(id, dto);

        if(!result)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var user = userService.findByUsername(dto.getUsername());

        userService.updateUser(user);

        if(result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    private static boolean isNullOrEmpty(String... strArr){
        for (String st : strArr) {
            if  (st==null || st.equals(""))
                return true;

        }
        return false;
    }
}



