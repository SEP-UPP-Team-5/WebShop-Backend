package com.example.webshop.service.impl;

import com.example.webshop.model.Order;
import com.example.webshop.repository.CartRepository;
import com.example.webshop.repository.OrderRepository;
import com.example.webshop.repository.UserRepository;
import com.example.webshop.service.OrderService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Order addOrder(Order order, String cartId) {
        if(!userRepository.findById(order.getUserId()).isPresent())
            return null;
        order.setId(UUID.randomUUID().toString());
        orderRepository.save(order);
        cartRepository.deleteById(cartId);
        return order;
    }
}
