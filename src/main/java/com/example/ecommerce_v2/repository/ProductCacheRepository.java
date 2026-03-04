//package com.example.ecommerce_v2.repository;
//
//import com.example.ecommerce_v2.mapper.ProductMapper;
//import com.example.ecommerce_v2.model.dto.Response.ProductResponseDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@RequiredArgsConstructor
//public class ProductCacheRepository {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final ProductRepository repository;
//    private final ProductMapper mapper;
//
//    private static final String KEY = "products:all";
//
//    public List<ProductResponseDto> getAll(){
//
//        List<ProductResponseDto> cachedProducts =
//                (List<ProductResponseDto>) redisTemplate.opsForValue().get(KEY);
//
//        if (cachedProducts != null) {
//            return cachedProducts;
//        }
//
//        List<ProductResponseDto> products = repository.findAll()
//                .stream()
//                .map(mapper::toResponseDto)
//                .toList();
//
//        redisTemplate.opsForValue()
//                .set(KEY, products, 10, TimeUnit.MINUTES);
//
//        return products;
//    }
//}