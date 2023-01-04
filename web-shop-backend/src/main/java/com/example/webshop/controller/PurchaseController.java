package com.example.webshop.controller;

import com.example.webshop.dto.ProductDto;
import com.example.webshop.dto.ProductPurchaseDto;
import com.example.webshop.dto.mapper.PurchaseMapper;
import com.example.webshop.model.*;
import com.example.webshop.service.CartService;
import com.example.webshop.service.ProductPurchaseService;
import com.example.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductPurchaseService purchaseService;

    @Autowired
    private CartService cartService;

    final private PurchaseMapper purchaseMapper = new PurchaseMapper();

    @GetMapping("/products")
    public List<ProductDto> getProducts(){
        List<ProductDto> dtoList = new ArrayList<>();
        for(Product product : productService.getProducts())
            dtoList.add(purchaseMapper.ProductToProductDto(product));
        return dtoList;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id){
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()) {
            return new ResponseEntity<>(purchaseMapper.ProductToProductDto(product.get()), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products/addNew")
    public ResponseEntity<String> saveProduct(@RequestBody ProductDto dto){
        if(isNullOrEmpty(dto.getName(), dto.getDescription(), dto.getPrice().toString()))
            return new ResponseEntity<>("None of fields cannot be empty!", HttpStatus.BAD_REQUEST);

        Product product = purchaseMapper.AddProductDtoToProduct(dto);
        return new ResponseEntity<>("Added product with id " + productService.addProduct(product).getId(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public List<ProductPurchaseDto> getPurchasesByUserId(@PathVariable String userId){
        List<ProductPurchaseDto> dtoList = new ArrayList<>();
        for(ProductPurchase purchase : purchaseService.getAllByUserId(userId))
            dtoList.add(purchaseMapper.ProductPurchaseToProductPurchaseDto(purchase));
        return dtoList;
    }

    @PostMapping("/addNew")
    public ResponseEntity<String> buyProduct(@RequestBody ProductPurchaseDto dto){
        if(isNullOrEmpty(dto.getUserId(), dto.getProductId(), dto.getCurrentPrice().toString()))
            return new ResponseEntity<>("None of fields cannot be empty!", HttpStatus.BAD_REQUEST);

        ProductPurchase productPurchase = purchaseMapper.AddProductPurchaseDtoToProductPurchase(dto);
        ProductPurchase saved = purchaseService.addProductPurchase(productPurchase);
        if(saved == null)
            return new ResponseEntity<>("User or product does not exist!", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Added purchase with id " + saved.getId(), HttpStatus.OK);
    }


    @PostMapping("/cart")
    public ResponseEntity<String> addEmptyCart(@RequestBody Cart cart){
        if(isNullOrEmpty(cart.getUserId()))
            return new ResponseEntity<>("User id cannot be empty!", HttpStatus.BAD_REQUEST);
        if(cartService.findByUserId(cart.getUserId()).isPresent())
            return new ResponseEntity<>("Cart for user already exists!", HttpStatus.BAD_REQUEST);

        Cart saved = cartService.addCart(new Cart(cart.getUserId()));
        if(saved == null)
            return new ResponseEntity<>("User does not exist!", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Added cart for user with id " + saved.getUserId(), HttpStatus.OK);
    }

    @PostMapping("/items/{userId}")
    public ResponseEntity<String> addItemToCart(@RequestBody CartItem item, @PathVariable String userId){
        if(isNullOrEmpty(item.getQuantity().toString(), item.getProductId()))
            return new ResponseEntity<>("None of fields cannot be empty!", HttpStatus.BAD_REQUEST);
        Cart saved = cartService.addItemToCart(item, userId);
        if(saved == null)
            return new ResponseEntity<>("Cart or user does not exist!", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Added item with product id " + item.getProductId(), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteCart(@PathVariable String userId){
        Optional<Cart> cart = cartService.findByUserId(userId);
        if(!cart.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        cartService.deleteById(cart.get().getId());
        return new ResponseEntity<>("Cart deleted for user with id " + userId, HttpStatus.OK);
    }

    @PutMapping("/cart/{userId}")
    public ResponseEntity<String> emptyCart(@PathVariable String userId){
        Optional<Cart> cart = cartService.findByUserId(userId);
        if(!cart.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        cart.get().setItems(new ArrayList<CartItem>());
        cartService.updateCart(cart.get());
        return new ResponseEntity<>("Cart updated for user with id " + userId, HttpStatus.OK);
    }

    private static boolean isNullOrEmpty(String... strArr){
        for (String st : strArr) {
            if  (st==null || st.equals(""))
                return true;

        }
        return false;
    }

}
