package com.example.ecommerce_v2.service;

import com.example.ecommerce_v2.mapper.ProductMapper;
import com.example.ecommerce_v2.model.dto.Request.ProductRequestDto;
import com.example.ecommerce_v2.model.dto.Response.ProductResponseDto;
import com.example.ecommerce_v2.model.entity.Product;
import com.example.ecommerce_v2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

//feign apisi

public ProductResponseDto getProductById(Long id) {

    log.info("Feign API: Getting product by id={}", id);

    String key = "product:" + id;

    // 1️⃣ Cache yoxla
    ProductResponseDto cachedProduct =
            (ProductResponseDto) redisTemplate.opsForValue().get(key);

    if (cachedProduct != null) {
        log.info("Product found in cache for id={}", id);
        return cachedProduct;
    }

    // 2️⃣ DB-dən gətir (SƏNİN ORİJİNAL KODUN)
    Product product = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"));

    ProductResponseDto response = mapper.toResponseDto(product);

    // 3️⃣ Cache-ə yaz (TTL ilə)
    redisTemplate.opsForValue()
            .set(key, response, 10, TimeUnit.MINUTES);

    log.info("Product cached for id={}", id);

    return response;
}
//crud apiler
    public ProductResponseDto save(ProductRequestDto dto) {
        log.info("Saving new product: {}", dto.getName());
        Product product = mapper.toEntity(dto);
        Product savedProduct = repository.save(product);
        return mapper.toResponseDto(savedProduct);
    }


    @Cacheable(
            value = "products",
            key = "#id"
    )
    public ProductResponseDto getById(Long id) {
        log.info("Getting product by id={} with cache", id);
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return mapper.toResponseDto(product);
    }

    public List<ProductResponseDto> getAll() {
        log.info("Getting all products");
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDto)
                .toList();
    }


    @CacheEvict(value = "products", key = "#id")
    public ProductResponseDto update(Long id, ProductRequestDto request) {
        log.info("Updating product id={}", id);
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCount(request.getCount());
        product.setActive(request.getActive());

        Product updated = repository.save(product);
        return mapper.toResponseDto(updated);
    }

    public void delete(Long id) {
        log.info("Deleting product id={}", id);
        if (!repository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        repository.deleteById(id);
    }

}
//create,get,update,delete