package com.example.webshop.service;

import com.example.webshop.model.Order;

public interface OrderService {
    Order addOrder(Order order, String cartId);
    Boolean markAsPayed(String id);

    Order findOne(String orderId);
}
