package com.example.webshop.repository;

import com.example.webshop.model.ProductPurchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductPurchaseRepository extends MongoRepository<ProductPurchase, String> {
    List<ProductPurchase> findAllByUserId(String id);
    @Query("{payPalOrderId:'?0'}")
    ProductPurchase findProductPurchaseByPayPalOrderId(String payPalOrderId);


}
