package com.example.webshop.service.impl;

import com.example.webshop.model.Order;
import com.example.webshop.model.Product;
import com.example.webshop.repository.OrderRepository;
import com.example.webshop.repository.ProductRepository;
import com.example.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private OrderRepository orderRepository;

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

    @Override
    public List<Product> getMyProducts(String id) {
        List<Order> orders = orderRepository.findByUserIdAndIsPaid(id, true);
        List<String> productIds = new ArrayList<>();

        for (Order order: orders) {
            for (String productId: order.getProductIds()) {
                productIds.add(productId);
            }
        }

        List<Product> products = new ArrayList<>();
        for (String productId: productIds) {
            products.add(repository.findById(productId).get());
        }

        return products;
    }
}
