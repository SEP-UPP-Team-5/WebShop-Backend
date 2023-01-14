package com.example.webshop.controller;

import com.example.webshop.dto.*;
import com.example.webshop.dto.mapper.PurchaseMapper;
import com.example.webshop.model.*;
import com.example.webshop.service.CartService;
import com.example.webshop.model.Product;
import com.example.webshop.model.ProductPurchase;
import com.example.webshop.repository.ProductPurchaseRepository;
import com.example.webshop.service.OrderService;
import com.example.webshop.service.ProductPurchaseService;
import com.example.webshop.service.ProductService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
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
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Value("${spring.application.name}")
    private String applicationName;

    final private PurchaseMapper purchaseMapper = new PurchaseMapper();

    @GetMapping("/products")
    public List<ProductDto> getProducts() {
        List<ProductDto> dtoList = new ArrayList<>();
        for (Product product : productService.getProducts())
            dtoList.add(purchaseMapper.ProductToProductDto(product));
        return dtoList;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(purchaseMapper.ProductToProductDto(product.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products/addNew")
    public ResponseEntity<String> saveProduct(@RequestBody ProductDto dto) {
        if (isNullOrEmpty(dto.getName(), dto.getDescription(), dto.getPrice().toString()))
            return new ResponseEntity<>("None of fields cannot be empty!", HttpStatus.BAD_REQUEST);

        Product product = purchaseMapper.AddProductDtoToProduct(dto);
        return new ResponseEntity<>("Added product with id " + productService.addProduct(product).getId(), HttpStatus.OK);
    }


//    @PostMapping("/addNew")
//    public ResponseEntity<String> buyProduct(@RequestBody ProductPurchaseDto dto) {
//        if (isNullOrEmpty(dto.getUserId(), dto.getProductId(), dto.getCurrentPrice().toString()))
//            return new ResponseEntity<>("None of fields cannot be empty!", HttpStatus.BAD_REQUEST);
//
//        ProductPurchase productPurchase = purchaseMapper.AddProductPurchaseDtoToProductPurchase(dto);
//        ProductPurchase saved = purchaseService.addProductPurchase(productPurchase);
//        if (saved == null)
//            return new ResponseEntity<>("User or product does not exist!", HttpStatus.BAD_REQUEST);
//
//        /* CONTACT WITH PAYPAL*/
//
//        //String token = accessToken();
//
//        //String paypalUrl = "http://localhost:8082/orders/create";
//        //"https://paypal-service/orders/create";
//
//        ServiceInstance serviceInstance = loadBalancerClient.choose("paypal-service");
//        //String uri = "https://host.docker.internal/orders/create";
//        URI url = serviceInstance.getUri();
//        System.out.println(url);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        //TODO: headers.setBearerAuth(token);
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("applicationName", applicationName);
//            obj.put("price", saved.getCurrentPrice());
//            obj.put("purchaseId", saved.getId());
//            obj.put("merchantId", "BAGSGQXCCH7WU");    //TODO seller credentials
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        HttpEntity<String> request = new HttpEntity<>(obj.toString(), headers);
//
////        CreatePaymentResponseDTO payPalResponse = getRestTemplate().postForObject(url + "/orders/create", request, CreatePaymentResponseDTO.class);   //TODO response class
////        System.out.println("CreatePaymentResponseDTO");
////        System.out.println(payPalResponse);
////        saved.setPayPalOrderId(payPalResponse.getPayPalOrderId());
//        repository.save(saved);
//
//        /* END OF CONTACT */
//        //TODO response link for redirect
//        return new ResponseEntity<>("Added purchase with id " + saved.getId(), HttpStatus.OK);
//    }

    @PostMapping("/items/{userId}")
    public ResponseEntity<String> addItemToCart(@RequestBody CartItem item, @PathVariable String userId) {
        if (isNullOrEmpty(item.getProductId()))
            return new ResponseEntity<>("None of fields cannot be empty!", HttpStatus.BAD_REQUEST);
        Cart saved = cartService.addItemToCart(item, userId);
        if (saved == null)
            return new ResponseEntity<>("User or product does not exist!", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Added item with product id " + item.getProductId(), HttpStatus.OK);
    }

    @PutMapping("/cart/{userId}")
    public ResponseEntity<String> emptyCart(@PathVariable String userId) {
        Optional<Cart> cart = cartService.findByUserId(userId);
        if (!cart.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        cart.get().setItems(new ArrayList<CartItem>());
        cartService.updateCart(cart.get());
        return new ResponseEntity<>("Cart updated for user with id " + userId, HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<String> saveOrder(@RequestBody OrderDto dto) {
        if (isNullOrEmpty(dto.getCart().getId(),dto.getCart().getUserId(), dto.getTotalPrice().toString()) || dto.getCart().getItems().isEmpty())
            return new ResponseEntity<>("None of fields cannot be empty!", HttpStatus.BAD_REQUEST);

        Order order = purchaseMapper.OrderDtoToOrder(dto);
        Order saved = orderService.addOrder(order, dto.getCart().getId());

        if(saved == null)
            return new ResponseEntity<>("User does not exist!", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Added order with id " + saved.getId(), HttpStatus.OK);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable String userId) {
        Optional<Cart> cart = cartService.findByUserId(userId);
        if (cart.isPresent()) {
            return new ResponseEntity<>(cart.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/confirm")
//    public String paymentConfirmation (@RequestBody ProductPurchaseConfirmationDto dto){
//        System.out.println(dto.getPurchaseId());
//
//        purchaseService.markAsPayed(dto.getPurchaseId());
//
//        return "paid";
//
//    }

    private static boolean isNullOrEmpty (String...strArr){
        for (String st : strArr) {
            if (st == null || st.equals(""))
                return true;

        }
        return false;
    }

}
