package com.example.webshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Product purchase")
public class ProductPurchase {
    @Id
    private String id;

    private String userId;
    private String productId;
    private Double currentPrice;
    private Boolean isPaid;
    private String payPalOrderId;


}
