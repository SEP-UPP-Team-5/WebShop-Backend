package com.example.webshop.dto;

import com.example.webshop.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private String id;

    @NotEmpty(message = "Username cannot be null or empty!")
    private String username;
    @NotEmpty(message = "Name cannot be null or empty!")
    private String name;
    @Email(message = "Email should be valid!")
    private String email;
    @NotEmpty(message = "Telephone number cannot be null or empty!")
    private String telephoneNo;
    @NotEmpty(message = "Gender cannot be null or empty!")
    private String gender;
    @NotEmpty(message = "Date of birth cannot be null or empty!")
    private Date dateOfBirth;

    @NotEmpty(message = "Address cannot be null or empty!")
    private String street;
    @NotEmpty(message = "Address cannot be null or empty!")
    private String streetNumber;
    @NotEmpty(message = "Address cannot be null or empty!")
    private String city;
    @NotEmpty(message = "Address cannot be null or empty!")
    private String postalCode;

    @NotEmpty(message = "Role cannot be null or empty!")
    private String role;

    public UserDto(String id, String username, String name, String email, String telephoneNo,
                   String gender, Date dateOfBirth, String street, String streetNumber, String city, String postalCode, String role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.telephoneNo = telephoneNo;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.role = role;
    }
}
