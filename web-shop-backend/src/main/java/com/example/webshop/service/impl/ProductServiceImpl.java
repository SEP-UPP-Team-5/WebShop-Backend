package com.example.webshop.service.impl;

import com.example.webshop.model.Product;
import com.example.webshop.repository.ProductRepository;
import com.example.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        product.setId(UUID.randomUUID().toString());
        repository.save(product);
        return product;
    }

    @Override
    public Optional<Product> findById(String id) {
        return repository.findById(id);
    }
}
