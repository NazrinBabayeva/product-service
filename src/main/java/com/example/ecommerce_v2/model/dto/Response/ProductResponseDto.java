package com.example.ecommerce_v2.model.dto.Response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {

    private Long id;
    private String name;
    private String description;
    private Long count;
    private BigDecimal price;
    private Boolean active;
}
