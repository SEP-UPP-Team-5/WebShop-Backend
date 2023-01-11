package com.example.webshop.repository;

import com.example.webshop.model.Order;
import com.example.webshop.model.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {


}
