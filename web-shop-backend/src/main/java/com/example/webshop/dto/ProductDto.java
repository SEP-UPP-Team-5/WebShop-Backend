package com.example.webshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    @NotEmpty(message = "Name cannot be null or empty!")
    private String name;
    @NotEmpty(message = "Description cannot be null or empty!")
    private String description;
    @NotEmpty(message = "Price cannot be null or empty!")
    private Double price;
}
