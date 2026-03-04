package com.example.ecommerce_v2.service;

import com.example.ecommerce_v2.mapper.ProductMapper;
import com.example.ecommerce_v2.model.dto.Request.ProductRequestDto;
import com.example.ecommerce_v2.model.dto.Response.ProductResponseDto;
import com.example.ecommerce_v2.model.entity.Product;

//import com.example.ecommerce_v2.repository.ProductCacheRepository;
import com.example.ecommerce_v2.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository repository;
    //private final ProductCacheRepository cacheRepository;
    private final ProductMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private static final String CACHE_PREFIX = "product:";
    private static final long TTL = 10;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }



    //feign apisi
    public ProductResponseDto getProductById(Long id) {
        log.info("Feign API: Getting product by id={}", id);
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        return mapper.toResponseDto(product);
    }
//crud apiler
    public ProductResponseDto save(ProductRequestDto dto) {
        log.info("Saving new product: {}", dto.getName());
        Product product = mapper.toEntity(dto);
        Product savedProduct = repository.save(product);
        return mapper.toResponseDto(savedProduct);
    }


    //with cache-aside
    public ProductResponseDto getById(Long id) {
        String key = CACHE_PREFIX + id;

        ProductResponseDto cached = getFromCache(key);
        if(cached != null){
            return cached;
        }

        return getFromDb(id, key);

    }

    private ProductResponseDto getFromCache(String key){
        ProductResponseDto cached = (ProductResponseDto) redisTemplate.opsForValue().get(key);
        if (cached != null){
            log.info("cache hit for key: {}", key);
        }
        else {
            log.warn("cache miss for key: {}", key);
        }
        return cached;
    }

    private ProductResponseDto getFromDb(Long id, String key){
        log.info("fetching from db. id: {}", id);

        Product product= repository.findById(id)
                .orElseThrow(() ->
        { log.error("product not found in db. id: " , id);
        return new RuntimeException("Product not found");
        });
        ProductResponseDto response = mapper.toResponseDto(product);

        redisTemplate.opsForValue().set(key, response, TTL, TimeUnit.MINUTES);
        log.info("Product cached. Key: {} , TTL: {} minutes", key, TTL);
        return response;
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