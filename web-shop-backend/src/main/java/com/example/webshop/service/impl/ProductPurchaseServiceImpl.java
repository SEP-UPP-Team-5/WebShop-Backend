package com.example.webshop.service.impl;

import com.example.webshop.model.ProductPurchase;
import com.example.webshop.repository.ProductPurchaseRepository;
import com.example.webshop.repository.ProductRepository;
import com.example.webshop.repository.UserRepository;
import com.example.webshop.service.ProductPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductPurchaseServiceImpl implements ProductPurchaseService {
    @Autowired
    private ProductPurchaseRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductPurchase> getAllByUserId(String id) {
        return repository.findAllByUserId(id);
    }

    @Override
    public ProductPurchase addProductPurchase(ProductPurchase purchase) {
        if(!userRepository.findById(purchase.getUserId()).isPresent())
            return null;
        if(!productRepository.findById(purchase.getProductId()).isPresent())
            return null;

        purchase.setId(UUID.randomUUID().toString());
        repository.save(purchase);
        return purchase;
    }
}
