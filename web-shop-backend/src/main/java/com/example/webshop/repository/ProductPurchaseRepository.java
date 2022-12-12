package com.example.webshop.repository;

import com.example.webshop.model.ProductPurchase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductPurchaseRepository extends MongoRepository<ProductPurchase, String> {
    List<ProductPurchase> findAllByUserId(String id);
}
