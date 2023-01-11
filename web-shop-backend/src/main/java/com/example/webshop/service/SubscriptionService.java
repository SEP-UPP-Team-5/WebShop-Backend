package com.example.webshop.service;

import com.example.webshop.model.Subscription;

public interface SubscriptionService {

    void newSubscription(Subscription subscription);
    Subscription findOne(String id);
}
