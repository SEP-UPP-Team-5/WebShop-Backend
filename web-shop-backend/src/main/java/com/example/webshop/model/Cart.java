package com.example.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Cart")
public class Cart {
    @Id
    private String id;
    private String userId;
    private List<CartItem> items;

    public Cart(String userId) {
        this.userId = userId;
        this.items = new ArrayList<CartItem>();
    }
}
