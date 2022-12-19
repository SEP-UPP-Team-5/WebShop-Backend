package com.example.webshop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "User")
public class User {

    @Id
    private String id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String telephoneNo;
    private Gender gender;
    private Date dateOfBirth;
    private Address address;

    private Role role;

    public User(String id, String username, String name, String email, String telephoneNo,
                Gender gender, Date dateOfBirth, Address address, Role role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.telephoneNo = telephoneNo;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.role = role;
    }

    public User(String id, String username, String password, String name, String email, String telephoneNo,
                Gender gender, Date dateOfBirth, Address address, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.telephoneNo = telephoneNo;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.role = role;
    }
}
