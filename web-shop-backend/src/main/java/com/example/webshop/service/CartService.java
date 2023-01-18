package com.example.webshop.service;

import com.example.webshop.model.Cart;
import com.example.webshop.model.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Optional<Cart> findByUserId(String userId);
    Cart updateCart(Cart cartWithNewData);
    void deleteById(String id);
    void deleteByUserId(String userId);
    Cart addCart(Cart cart);

    Cart addItemToCart(CartItem item, String userId);
    List<Cart> getAll();
}
