package com.example.webshop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "Address")
public class Address {
    @Id
    private String id;

    private String street;
    private String streetNumber;
    private String city;
    private String postalCode;
    private String country;
    private Double latitude;
    private Double longitude;

    public Address(String id, String street, String streetNumber, String city, String postalCode,
                   String country, Double latitude, Double longitude) {
        this.id = id;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
