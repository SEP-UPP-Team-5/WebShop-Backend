package com.example.webshop.dto.mapper;

import com.example.webshop.dto.OrderDto;
import com.example.webshop.dto.ProductDto;
import com.example.webshop.dto.ProductPurchaseDto;
import com.example.webshop.model.CartItem;
import com.example.webshop.model.Order;
import com.example.webshop.model.Product;
import com.example.webshop.model.ProductPurchase;

import java.util.ArrayList;
import java.util.List;

public class PurchaseMapper {
    public ProductDto ProductToProductDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
    public Product AddProductDtoToProduct(ProductDto dto){
        return new Product(dto.getId(), dto.getName(), dto.getDescription(), dto.getPrice());
    }

    public ProductPurchaseDto ProductPurchaseToProductPurchaseDto(ProductPurchase purchase) {
        return new ProductPurchaseDto(purchase.getId(),purchase.getUserId(), purchase.getProductId(), purchase.getCurrentPrice(), purchase.getIsPaid(), purchase.getPayPalOrderId());
    }
    public ProductPurchase AddProductPurchaseDtoToProductPurchase(ProductPurchaseDto dto){
        return new ProductPurchase(dto.getId(),dto.getUserId(), dto.getProductId(), dto.getCurrentPrice(), dto.getIsPaid(), dto.getPayPalOrderId());
    }

    public Order OrderDtoToOrder(OrderDto dto){
        List<String> ids = new ArrayList<String>();
        for (CartItem item : dto.getCart().getItems()) {
            ids.add(item.getProductId());
        }
        return new Order(dto.getId(), dto.getCart().getUserId(), ids, dto.getTotalPrice(), dto.getIsPaid());
    }
}
