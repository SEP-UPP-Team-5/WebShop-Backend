package com.example.webshop.dto;

import com.example.webshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryDto {
    private String orderId;
    private List<Product> products;
    private Double totalPrice;
    private Boolean isPaid;
    private Date paymentTime;
}
