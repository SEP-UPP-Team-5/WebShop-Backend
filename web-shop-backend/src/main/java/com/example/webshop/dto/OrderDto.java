package com.example.webshop.dto;

import com.example.webshop.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String id;
   // @NotEmpty
    private Cart cart;
    @NotEmpty
    private Double totalPrice;
    private Boolean isPaid;

}
