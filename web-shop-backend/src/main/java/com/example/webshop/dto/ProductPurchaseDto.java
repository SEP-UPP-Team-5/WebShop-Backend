package com.example.webshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPurchaseDto {
    private String id;
    @NotEmpty(message = "User cannot be null or empty!")
    private String userId;
    @NotEmpty(message = "Product cannot be null or empty!")
    private String productId;
    @NotEmpty(message = "Price cannot be null or empty!")
    private Double currentPrice;
    @NotEmpty
    private Boolean isPaid;
    @NotEmpty
    private String payPalOrderId;
}
