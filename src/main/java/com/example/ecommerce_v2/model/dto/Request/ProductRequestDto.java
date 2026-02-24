package com.example.ecommerce_v2.model.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductRequestDto  {
    private String name;
    private String description;
    private Long count;
    private BigDecimal price;
    private Boolean active;

}

//create, update