package com.example.ecommerce_v2.mapper;

import com.example.ecommerce_v2.model.dto.Request.ProductRequestDto;
import com.example.ecommerce_v2.model.dto.Response.ProductResponseDto;
import com.example.ecommerce_v2.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "count", target = "count")
    ProductRequestDto toRequestDto(Product product);

    Product toEntity(ProductRequestDto dto);

    ProductResponseDto toResponseDto(Product product);

    Product toEntity(ProductResponseDto dto);

}
