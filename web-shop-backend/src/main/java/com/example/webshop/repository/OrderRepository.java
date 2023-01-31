package com.example.webshop.repository;

import com.example.webshop.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserIdAndIsPaid(String userId, Boolean isPaid);
}
