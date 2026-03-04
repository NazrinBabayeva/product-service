package com.example.ecommerce_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ECommerceV2Application {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceV2Application.class, args);
    }

}
