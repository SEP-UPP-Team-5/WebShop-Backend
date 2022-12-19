package com.example.webshop.controller;

import com.example.webshop.dto.ProductDto;
import com.example.webshop.dto.ProductPurchaseDto;
import com.example.webshop.dto.mapper.PurchaseMapper;
import com.example.webshop.model.Product;
import com.example.webshop.model.ProductPurchase;
import com.example.webshop.service.ProductPurchaseService;
import com.example.webshop.service.ProductService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductPurchaseService purchaseService;

    @Value("${spring.application.name}")
    private String applicationName;

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

        /* CONTACT WITH PAYPAL*/

        //String token = accessToken();

        String paypalUrl = "https://paypal-service/orders";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //TODO: headers.setBearerAuth(token);
        JSONObject obj = new JSONObject();
        try {
            obj.put("applicationName", applicationName);
            obj.put("price", saved.getCurrentPrice());
            obj.put("purchaseId", saved.getId());
            obj.put("payPalId", "");    //TODO seller credentials
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpEntity<String> request = new HttpEntity<>(obj.toString(), headers);
        Product productResponse = restTemplate.postForObject(paypalUrl, request, Product.class);    //TODO response class
        System.out.println(productResponse);

        /* END OF CONTACT */
        //TODO response link for redirect
        return new ResponseEntity<>("Added purchase with id " + saved.getId(), HttpStatus.OK);
    }

    private static boolean isNullOrEmpty(String... strArr){
        for (String st : strArr) {
            if  (st==null || st.equals(""))
                return true;

        }
        return false;
    }

}
