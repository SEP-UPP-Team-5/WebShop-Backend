package com.example.webshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AddUserDto {

    private String id;

    @NotEmpty(message = "Username cannot be null or empty!")
    @Pattern(regexp = "^[a-zA-Z0-9_.]$",
            message = "Username must contain only letters, numbers, dot and underscore.")
    private String username;
    @NotEmpty(message = "Name cannot be null or empty!")
    @Pattern(regexp = "^[a-zA-Z]$",
            message = "Name must contain only letters.")
    private String name;
    @Email(message = "Email should be valid!")
    private String email;
    @NotEmpty(message = "Telephone number cannot be null or empty!")
    @Pattern(regexp = "^[0-9]$",
            message = "Telephone number must contain only numbers.")
    private String telephoneNo;

    private String gender;
    @NotEmpty(message = "Date of birth cannot be null or empty!")
    private Date dateOfBirth;

    private String street;
    private String streetNumber;
    private String city;
    private String postalCode;

    @NotEmpty(message = "Role cannot be null or empty!")
    private String role;

    @NotEmpty(message = "Password cannot be null or empty!")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "Password must contain minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character.")
    private String password;


    public AddUserDto(String id, String username, String password, String name, String email, String telephoneNo,
                      String gender, Date dateOfBirth, String street, String streetNumber, String city, String postalCode, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.telephoneNo = telephoneNo;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.role=role;
    }
}
