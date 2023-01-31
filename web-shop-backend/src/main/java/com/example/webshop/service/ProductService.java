package com.example.webshop.service;

import com.example.webshop.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();
    Product addProduct(Product product);
    Optional<Product> findById(String id);

    List<Product> getMyProducts(String id);
}
