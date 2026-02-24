package com.example.ecommerce_v2.controller;

import com.example.ecommerce_v2.model.dto.Request.ProductRequestDto;
import com.example.ecommerce_v2.model.dto.Response.ProductResponseDto;
import com.example.ecommerce_v2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminCotroller {

    private final ProductService service;

    @PostMapping
    public ProductResponseDto save(@RequestBody ProductRequestDto dto){
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(
            @PathVariable Long id,
            @RequestBody ProductRequestDto request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
