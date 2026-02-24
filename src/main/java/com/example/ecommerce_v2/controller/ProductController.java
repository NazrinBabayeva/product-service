package com.example.ecommerce_v2.controller;

import com.example.ecommerce_v2.model.dto.Request.ProductRequestDto;
import com.example.ecommerce_v2.model.dto.Response.ProductResponseDto;
import com.example.ecommerce_v2.model.entity.Product;
import com.example.ecommerce_v2.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    @GetMapping("/{id}") // indi URL /products/{id} oldu
    public ProductResponseDto getProductById(@PathVariable Long id) {
        log.info("GET /api/products/{} çağırıldı", id);
        return service.getProductById(id);

    }
}
