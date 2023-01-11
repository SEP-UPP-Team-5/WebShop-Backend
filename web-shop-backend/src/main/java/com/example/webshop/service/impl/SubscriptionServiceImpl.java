package com.example.webshop.service.impl;

import com.example.webshop.model.Subscription;
import com.example.webshop.repository.SubscriptionRepository;
import com.example.webshop.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;


    private Boolean subscriptionExists() {

        return !subscriptionRepository.findAll().isEmpty();
    }

    @Override
    public void newSubscription(Subscription newSubscription) {
      newSubscription.setId("1");
      subscriptionRepository.save(newSubscription);
    }

    @Override
    public Subscription findOne(String id){
        return subscriptionRepository.findById(id).get();
    }


}
