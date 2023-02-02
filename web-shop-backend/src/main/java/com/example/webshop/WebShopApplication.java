package com.example.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableAutoConfiguration
public class WebShopApplication {

	@Bean
	public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

	public static void main(String[] args) {
		SpringApplication.run(WebShopApplication.class, args);
	}




}
