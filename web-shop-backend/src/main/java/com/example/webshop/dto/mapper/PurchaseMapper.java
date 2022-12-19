package com.example.webshop.dto.mapper;

import com.example.webshop.dto.ProductDto;
import com.example.webshop.dto.ProductPurchaseDto;
import com.example.webshop.model.Product;
import com.example.webshop.model.ProductPurchase;

public class PurchaseMapper {
    public ProductDto ProductToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
    public Product AddProductDtoToProduct(ProductDto dto){
        return new Product(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice());
    }

    public ProductPurchaseDto ProductPurchaseToProductPurchaseDto(ProductPurchase purchase) {
        return new ProductPurchaseDto(purchase.getId(),purchase.getUserId(), purchase.getProductId(), purchase.getCurrentPrice());
    }
    public ProductPurchase AddProductPurchaseDtoToProductPurchase(ProductPurchaseDto dto){
        return new ProductPurchase(dto.getId(),dto.getUserId(), dto.getProductId(), dto.getCurrentPrice());
    }
}
