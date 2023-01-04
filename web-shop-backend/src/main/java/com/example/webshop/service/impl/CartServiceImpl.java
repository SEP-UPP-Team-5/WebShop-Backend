package com.example.webshop.service.impl;

import com.example.webshop.model.Cart;
import com.example.webshop.model.CartItem;
import com.example.webshop.model.User;
import com.example.webshop.repository.CartRepository;
import com.example.webshop.repository.UserRepository;
import com.example.webshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Override
    public Optional<Cart> findByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart updateCart(Cart cartWithNewData) {
        Optional<Cart> cart = findByUserId(cartWithNewData.getUserId());
        return cartRepository.save(cartWithNewData);
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void deleteByUserId(String userId) {

    }

    @Override
    public Cart addCart(Cart cart) {
        cart.setId(UUID.randomUUID().toString());
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public Cart addItemToCart(CartItem item, String userId) {
        Optional<Cart> cart = findByUserId(userId);
        if(!cart.isPresent())
            return null;
        List<CartItem> items = cart.get().getItems();
        items.add(item);
        cart.get().setItems(items);
        return cartRepository.save(cart.get());
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }
}
