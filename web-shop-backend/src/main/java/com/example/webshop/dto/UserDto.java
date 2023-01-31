package com.example.webshop.dto;

import com.example.webshop.model.Product;
import lombok.Getter;
import lombok.Setter;
import org.intellij.lang.annotations.RegExp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserDto {

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
