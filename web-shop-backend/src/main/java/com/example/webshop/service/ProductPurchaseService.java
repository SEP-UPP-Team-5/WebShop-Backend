package com.example.webshop.service;

import com.example.webshop.model.ProductPurchase;

import java.util.List;

public interface ProductPurchaseService {
    List<ProductPurchase> getAllByUserId(String id);
    ProductPurchase addProductPurchase(ProductPurchase purchase);

    Boolean markAsPayed(String id);
}
